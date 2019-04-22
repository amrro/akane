package app.akane.data.repo.subreddit

import app.akane.data.RedditManager
import app.akane.data.entity.Subreddit
import app.akane.data.mapper.SubredditMapper
import javax.inject.Inject

class RemoteSubredditDataSource @Inject constructor(
    private val redditManager: RedditManager,
    private val mapper: SubredditMapper
) {

    suspend fun join(name: String) = redditManager.request {
        subreddit(name).subscribe()
    }

    suspend fun leave(name: String) = redditManager.request {
        subreddit(name).unsubscribe()
    }

    suspend fun about(name: String): Subreddit = redditManager.request {
        mapper(subreddit(name).about())
    }
}