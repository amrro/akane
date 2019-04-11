package app.akane.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_preview",
    indices = [
        Index("index", unique = true),
        Index("post_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = PostInfo::class,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            parentColumns = ["id"],
            childColumns = ["post_id"]
        )
    ]
)
data class ImagePreview(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var index: Long = 0,

    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "post_id")
    var postId: String,
    @ColumnInfo(name = "enabled")
    var enabled: Boolean = false,
    @ColumnInfo(name = "width")
    var width: Int = 0,
    @ColumnInfo(name = "height")
    var height: Int = 0,
    @ColumnInfo(name = "link")
    var link: String = "",
    @ColumnInfo(name = "type")
    var type: ImageType = ImageType.RESOLUTION
) {
    enum class ImageType {
        SOURCE,
        RESOLUTION
    }
}