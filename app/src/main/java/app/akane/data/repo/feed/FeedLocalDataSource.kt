package app.akane.data.repo.feed

import app.akane.data.dao.ImagePreviewDao
import app.akane.data.dao.PostDao
import app.akane.data.entity.Post
import app.akane.data.entity.PostInfo
import app.akane.util.notNullOrEmpty
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(
    private val postDao: PostDao,
    private val imagesDao: ImagePreviewDao
) {

    fun observeForPaging(subreddit: String?) =
        postDao.observeForPaging(subreddit.notNullOrEmpty {
            "Repository.subredditName(String): subreddit Name cannot be empty"
        })

    suspend fun insertAll(entries: List<Post>, reset: Boolean) {
        if (reset) {
            this.deleteAll(entries.first().postInfo.listing)
        }
        postDao.insertAll(entries.map { it.postInfo })
        imagesDao.insertAll(entries.flatMap { it.images })
    }

    private suspend fun deleteAll(subreddit: String) {
        postDao.deleteListing(subreddit)
    }

//    suspend fun insertImages(entries: List<ImagePreview>) {
//        imagesDao.insertAll(entries)
//    }

    suspend fun getPostWithId(id: String) = postDao.getPostWithId(id)

    suspend fun updatePost(new: PostInfo) = postDao.updatePost(new)
}