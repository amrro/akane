package app.akane.di

import app.akane.ui.auth.NewUserFragment
import app.akane.ui.feed.FeedListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module(includes = [AppAssistedModule::class])
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFeedListFragment(): FeedListFragment

    @ContributesAndroidInjector
    abstract fun contributeNewUserFragment(): NewUserFragment
}
