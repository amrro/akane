package app.akane.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.akane.data.entity.Post

@Dao
abstract class PostDao {

    @Query("SELECT * FROM reddit_feed WHERE listing LIKE :type ORDER BY `index` ASC")
    abstract fun observeForPaging(type: String): DataSource.Factory<Int, Post>

    @Insert
    abstract suspend fun insertAll(entires: List<Post>)

    @Query("DELETE FROM reddit_feed")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM reddit_feed WHERE listing LIKE :type")
    abstract suspend fun deleteListing(type: String)

    @Query("SELECT * FROM reddit_feed WHERE id LIKE :id")
    abstract suspend fun getPostWithId(id: String): Post

    @Update
    abstract suspend fun updatePost(post: Post)
}