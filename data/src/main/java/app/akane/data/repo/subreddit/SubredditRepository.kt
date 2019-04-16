package app.akane.data.repo.subreddit

import androidx.lifecycle.LiveData
import app.akane.data.entity.Subreddit
import javax.inject.Inject

class SubredditRepository @Inject constructor(
    private val remote: RemoteSubredditDataSource,
    private val local: LocalSubredditDataSource
) {

    fun subreddit(id: String): LiveData<Subreddit> {
//        val subreddit = remote.about(name)
//        local.insert(subreddit)
        return local.observeSubredditWithId(id)
    }

    suspend fun loadSubreddit(name: String) {
        val subreddit = remote.about(name)
        local.insert(subreddit)
    }

    suspend fun join(name: String) {
        // TODO:
        //      1. Make the join action.
        remote.join(name)

        //      2. store that locally.
        val newSubreddit = remote.about(name)
        local.insert(newSubreddit)
    }

    suspend fun leave(name: String) {
        remote.leave(name)
        val newSubreddit = remote.about(name)
        local.insert(newSubreddit)
    }

    suspend fun about(name: String) {
    }
}