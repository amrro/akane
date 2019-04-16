package app.akane.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import app.akane.data.entity.ImagePreview

@Dao
abstract class ImagePreviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entries: List<ImagePreview>)
}