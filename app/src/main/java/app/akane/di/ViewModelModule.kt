package app.akane.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.akane.ui.feed.FeedViewModel
import app.akane.util.AxiomViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindHomeViewModel(userViewModel: FeedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AxiomViewModelFactory): ViewModelProvider.Factory
}
