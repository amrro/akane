package app.akane.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.akane.data.dao.ImagePreviewDao
import app.akane.data.dao.PostDao
import app.akane.data.entity.ImagePreview
import app.akane.data.entity.PostInfo

@Database(
    entities = [
        PostInfo::class,
        ImagePreview::class
    ],
    version = 2
)
@TypeConverters(AkaneTypeConverters::class)
abstract class AkaneDataBase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun imagePreviewDao(): ImagePreviewDao
}