package app.akane.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.akane.ui.feed.home.HomeViewModel
import app.akane.util.AxiomViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindUserViewModel(userViewModel: HomeViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: AxiomViewModelFactory): ViewModelProvider.Factory
}
