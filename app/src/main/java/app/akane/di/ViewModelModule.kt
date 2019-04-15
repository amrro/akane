package app.akane.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.akane.ui.feed.ActionsViewModel
import app.akane.util.AxiomViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ActionsViewModel::class)
    abstract fun bindActionsViewModel(actionsViewModel: ActionsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AxiomViewModelFactory): ViewModelProvider.Factory
}
