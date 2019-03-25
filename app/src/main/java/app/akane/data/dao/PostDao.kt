package app.akane.data.dao

import androidx.paging.DataSource
import androidx.room.*
import app.akane.data.entity.Post
import app.akane.data.entity.PostInfo

@Dao
abstract class PostDao {

    @Transaction
    @Query("SELECT * FROM post_info WHERE listing LIKE :type ORDER BY `index` ASC")
    abstract fun observeForPaging(type: String): DataSource.Factory<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entires: List<PostInfo>)

    @Query("DELETE FROM post_info")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM post_info WHERE listing LIKE :type")
    abstract suspend fun deleteListing(type: String)

    @Query("SELECT * FROM post_info WHERE id LIKE :id")
    abstract suspend fun getPostWithId(id: String): PostInfo?

    @Update
    abstract suspend fun updatePost(post: PostInfo)
}