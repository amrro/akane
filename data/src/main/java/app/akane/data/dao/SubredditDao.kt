package app.akane.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.akane.data.entity.Subreddit

@Dao
abstract class SubredditDao {

    @Query("SELECT * FROM subreddits WHERE display_name LIKE :name")
    abstract fun observeSubreddit(name: String): LiveData<Subreddit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(subreddit: Subreddit)

    @Update
    abstract suspend fun updateSubreddit(subreddit: Subreddit)

    @Query("DELETE FROM subreddits")
    abstract suspend fun deleteAll()
}