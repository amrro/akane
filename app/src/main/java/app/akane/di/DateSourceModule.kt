package app.akane.di

import app.akane.repo.HomeFeedDataSource
import app.akane.repo.PopularFeedDataSource
import dagger.Module
import dagger.Provides
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Singleton


@Module
class DateSourceModule {

    @Singleton
    @Provides
    fun providesHomeFeedDataSource(accountHelper: AccountHelper) = HomeFeedDataSource.Factory(accountHelper)


    @Singleton
    @Provides
    fun providesPopularFeedDataSource(accountHelper: AccountHelper) = PopularFeedDataSource.Factory(accountHelper)

}