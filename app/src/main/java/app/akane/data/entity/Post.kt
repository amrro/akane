package app.akane.data.entity

import androidx.room.Embedded
import androidx.room.Relation

class Post {

    @Embedded
    var postInfo: PostInfo = PostInfo()

    @Relation(
        parentColumn = "id",
        entityColumn = "post_id"
    )
    var images: List<ImagePreview> = emptyList()
}