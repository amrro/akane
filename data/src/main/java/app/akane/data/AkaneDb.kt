package app.akane.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.akane.data.dao.ImagePreviewDao
import app.akane.data.dao.PostDao
import app.akane.data.dao.SubredditDao
import app.akane.data.entity.ImagePreview
import app.akane.data.entity.PostInfo
import app.akane.data.entity.Subreddit

@Database(
    entities = [
        PostInfo::class,
        ImagePreview::class,
        Subreddit::class
    ],
    version = 4
)
@TypeConverters(AkaneTypeConverters::class)
abstract class AkaneDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun imagePreviewDao(): ImagePreviewDao
    abstract fun subredditDao(): SubredditDao
}