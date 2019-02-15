package app.akane.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.akane.ui.feed.home.HomeViewModel
import app.akane.ui.feed.popular.PopularFeedViewModel
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
    abstract fun bindHomeViewModel(userViewModel: HomeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(PopularFeedViewModel::class)
    abstract fun bindPopularFeedViewModel(userViewModel: PopularFeedViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: AxiomViewModelFactory): ViewModelProvider.Factory
}
