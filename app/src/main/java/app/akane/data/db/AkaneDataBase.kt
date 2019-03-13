package app.akane.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.akane.data.dao.PostDao
import app.akane.data.entity.Post

@Database(
    entities = [
        Post::class
    ],
    version = 1
)
@TypeConverters(AkaneTypeConverters::class)
abstract class AkaneDataBase : RoomDatabase() {
    abstract fun postDao(): PostDao
}