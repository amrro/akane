package app.akane.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import net.dean.jraw.models.CommentSort
import net.dean.jraw.models.DistinguishedStatus
import net.dean.jraw.models.SubmissionPreview
import net.dean.jraw.models.VoteDirection
import org.threeten.bp.LocalDateTime

@Entity(
    tableName = "post_info",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
@Parcelize
data class PostInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var index: Long = 0,

    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "isArchived")
    var isArchived: Boolean = false,
    @ColumnInfo(name = "author")
    var author: String = "",
    @ColumnInfo(name = "vote")
    var vote: VoteDirection = VoteDirection.NONE,
    @ColumnInfo(name = "author_flair_text")
    var authorFlairText: String? = null,
    @ColumnInfo(name = "body")
    var body: String? = null,
    @ColumnInfo(name = "num_comments")
    var commentCount: Int = 0,
    @ColumnInfo(name = "contest_mode")
    var isContestMode: Boolean = false,
    @ColumnInfo(name = "created")
    var created: LocalDateTime? = null,
    @ColumnInfo(name = "distinguished")
    var distinguished: DistinguishedStatus = DistinguishedStatus.NORMAL,
    @ColumnInfo(name = "domain")
    var domain: String = "",
    @ColumnInfo(name = "edited")
    var edited: LocalDateTime? = null,
//    @Ignore
//    @ColumnInfo(name = "secure_media")
//    var embeddedMedia: EmbeddedMedia? = null,
    @ColumnInfo(name = "fullName")
    var fullName: String = "",
    @ColumnInfo(name = "can_gild")
    var isGildable: Boolean = true,
    @ColumnInfo(name = "gilded")
    var gilded: Short = 0,
    @ColumnInfo(name = "isHidden")
    var isHidden: Boolean = false,
    @ColumnInfo(name = "link_flair_text")
    var linkFlairText: String? = null,
    @ColumnInfo(name = "locked")
    var locked: Boolean = false,
    @ColumnInfo(name = "over_18")
    var isNsfw: Boolean = false,
    @ColumnInfo(name = "permalink")
    var permalink: String = "",
    @ColumnInfo(name = "post_hint")
    var postHint: String? = "",
    @ColumnInfo(name = "preview")
    @Ignore
    var preview: SubmissionPreview? = null,
    @ColumnInfo(name = "isQuarantine")
    var isQuarantine: Boolean = false,
    @ColumnInfo(name = "isRemoved")
    var isRemoved: Boolean = false,
    @ColumnInfo(name = "num_reports")
    var reports: Int = 0,
    @ColumnInfo(name = "isSaved")
    var isSaved: Boolean = false,
    @ColumnInfo(name = "score")
    var score: Int = 0,
    @ColumnInfo(name = "hide_score")
    var isScoreHidden: Boolean = false,
    @ColumnInfo(name = "is_self")
    var isSelfPost: Boolean = false,
    @ColumnInfo(name = "selftext")
    var selfText: String = "",
    @ColumnInfo(name = "isSpam")
    var isSpam: Boolean = false,
    @ColumnInfo(name = "isSpoiler")
    var isSpoiler: Boolean = false,
    @ColumnInfo(name = "stickied")
    var stickied: Boolean = false,
    @ColumnInfo(name = "subreddit")
    var subreddit: String = "",
    @ColumnInfo(name = "subreddit_full_name")
    var subredditFullName: String = "",

    /* TODO: Make this nullable. */
    @ColumnInfo(name = "suggested_sort")
    var suggestedSort: CommentSort = CommentSort.TOP,
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String? = null,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "isVisited")
    var isVisited: Boolean = false,

    @ColumnInfo(name = "listing")
    var listing: String = ""
) : Parcelable {

    constructor() : this(0)

    val linkToComments: String
        get() {
            return "$title\nhttps://www.reddit.com$permalink"
        }
}