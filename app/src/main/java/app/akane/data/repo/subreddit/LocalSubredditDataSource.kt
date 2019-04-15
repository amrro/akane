package app.akane.data.repo.subreddit

import app.akane.data.dao.SubredditDao
import app.akane.data.entity.Subreddit
import javax.inject.Inject

class LocalSubredditDataSource @Inject constructor(
    private val dao: SubredditDao
) {

    fun observeSubredditWithId(name: String) = dao.observeSubreddit(name)

    suspend fun insert(subreddit: Subreddit) = dao.insert(subreddit)

    suspend fun update(subreddit: Subreddit) = dao.updateSubreddit(subreddit)

    suspend fun deleteAll() = dao.deleteAll()
}