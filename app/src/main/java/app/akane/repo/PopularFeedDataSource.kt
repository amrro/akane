package app.akane.repo

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import net.dean.jraw.models.Submission
import net.dean.jraw.oauth.AccountHelper

class PopularFeedDataSource constructor(
        accountHelper: AccountHelper
) : PageKeyedDataSource<Int, Submission>() {


    class Factory(val accountHelper: AccountHelper) : DataSource.Factory<Int, Submission>() {
        override fun create(): DataSource<Int, Submission> {
            return PopularFeedDataSource(accountHelper)
        }
    }

    val popular = accountHelper
            .reddit
            .subreddit("popular")
            .posts()
            .limit(20)
            .build()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Submission>) {
        return callback.onResult(popular.next(),0,  30)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Submission>) {
        return callback.onResult(popular.next(), 0)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Submission>) {
    }
}