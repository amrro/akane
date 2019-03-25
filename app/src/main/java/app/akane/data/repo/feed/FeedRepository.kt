package app.akane.data.repo.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import app.akane.data.entity.Post
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val frontPageLocal: FeedLocalDataSource,
    private val frontPageRemote: FeedRemoteDataSource
) {

    private var subreddit: String? = null

    fun observeForPaging(
        callback: PagedList.BoundaryCallback<Post>
    ): LiveData<PagedList<Post>> {
        return frontPageLocal.observeForPaging(subreddit).toLiveData(
            config = PAGING_CONFIG,
            boundaryCallback = callback
        )
    }

    suspend fun refresh() {
        return updateFrontPage(true)
    }

    suspend fun loadMore() {
        return updateFrontPage(false)
    }

    fun updateConfigs(
        name: String,
        sort: SubredditSort,
        timePeriod: TimePeriod? = null
    ) {
        this.subreddit = name
        frontPageRemote.updateConfigs(name, sort, timePeriod)
    }

    @Throws(Exception::class)
    private suspend fun updateFrontPage(reset: Boolean) {
        if (reset) {
            frontPageRemote.restart()
        }

        frontPageRemote.nextPage().also { result ->
            frontPageLocal.insertAll(result, reset)
        }
    }

    companion object {
        private val PAGING_CONFIG = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
    }
}