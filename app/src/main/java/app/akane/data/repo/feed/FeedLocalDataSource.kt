package app.akane.data.repo.feed

import app.akane.data.dao.PostDao
import app.akane.data.entity.Post
import app.akane.util.notNullOrEmpty
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(
    private val postDao: PostDao
) {

    fun observeForPaging(subreddit: String?) =
        postDao.observeForPaging(subreddit.notNullOrEmpty {
            "Repository.subredditName(String): subreddit Name cannot be empty"
        })

    suspend fun insertAll(entries: List<Post>, reset: Boolean) {
        if (reset) {
            this.deleteAll(entries.first().listing)
        }
        postDao.insertAll(entries)
    }

    private suspend fun deleteAll(subreddit: String) {
        postDao.deleteListing(subreddit)
    }

    suspend fun getPostWithId(id: String) = postDao.getPostWithId(id)

    suspend fun updatePost(post: Post) = postDao.updatePost(post)
}