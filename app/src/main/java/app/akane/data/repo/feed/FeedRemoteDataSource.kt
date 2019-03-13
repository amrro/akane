package app.akane.data.repo.feed

import app.akane.data.entity.Post
import app.akane.data.mapper.SubmissionToPost
import app.akane.util.AppCoroutineDispatchers
import arrow.core.Try
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.dean.jraw.models.Submission
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import net.dean.jraw.oauth.AccountHelper
import net.dean.jraw.pagination.DefaultPaginator
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val accountHelper: AccountHelper,
    private val mapper: SubmissionToPost,
    private val dispatchers: AppCoroutineDispatchers
) {

    private lateinit var pagination: DefaultPaginator<Submission>


    fun updateConfigs(
        subredditName: String,
        sorting: SubredditSort = SubredditSort.HOT,
        timePeriod: TimePeriod? = null
    ) {
        // first guide mapper to use this in its map
        mapper.listing = subredditName

        // Prepare the new Pagination.
        val builder = if (subredditName == "frontpage") {
            accountHelper.reddit.frontPage()
        } else {
            accountHelper.reddit.subreddit(subredditName).posts()
        }

        timePeriod?.let { builder.timePeriod(timePeriod) }
        pagination = builder.sorting(sorting)
            .limit(LIMIT)
            .build()
    }


    fun restart() {
        require(::pagination.isInitialized) {
            "FeedRemoteDataSource.restart(): Pagination is not initialized. call FeedRemoteDataSource#updateConfigs() first."
        }
        pagination.restart()
    }

    fun nextPage(): Deferred<Try<List<Post>>> = GlobalScope.async(dispatchers.io) {
        require(::pagination.isInitialized) {
            "FeedRemoteDataSource.nextPage(): Pagination is not initialized. call FeedRemoteDataSource#updateConfigs() first."
        }

        Try { mapper(pagination.next().children) }
    }


    companion object {
        const val LIMIT = 40
    }
}