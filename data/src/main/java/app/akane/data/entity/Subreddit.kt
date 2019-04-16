package app.akane.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import net.dean.jraw.models.CommentSort
import net.dean.jraw.models.Subreddit
import org.threeten.bp.LocalDateTime

@Entity(
    tableName = "subreddits",
    indices = [
        Index(value = ["id"], unique = true),
    Index(value = ["name"], unique = true)
    ]
)
data class Subreddit(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var index: Long = 0,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val fullName: String,

    @ColumnInfo(name = "accounts_active")
    val accountsActive: Int?,

    @ColumnInfo(name = "accounts_active_fuzzed")
    val isAccountsActiveFuzzed: Boolean,

    @ColumnInfo(name = "banner_img")
    val bannerImage: String?,

    @ColumnInfo(name = "comment_score_hide_mins")
    val commentScoreHideMinsNullable: Int?,

    @ColumnInfo(name = "created_utc")
    val created: LocalDateTime,

    @ColumnInfo(name = "hide_ads")
    val hideAds: Boolean?,

    @ColumnInfo(name = "key_color")
    val keyColor: String?,

    @ColumnInfo(name = "display_name")
    val name: String,

    @ColumnInfo(name = "over18")
    val isNsfw: Boolean = false,

    @ColumnInfo(name = "public_description")
    val publicDescription: String,

    @ColumnInfo(name = "quarantine")
    val isQuarantined: Boolean = false,

    @ColumnInfo(name = "description")
    val sidebar: String?,

    @ColumnInfo(name = "spoilers_enabled")
    val isSpoilersEnabled: Boolean = false,

    @ColumnInfo(name = "submission_type")
    val submissionType: Subreddit.SubmissionType?,

    @ColumnInfo(name = "submit_link_label")
    val submitLinkLabel: String?,

    @ColumnInfo(name = "submit_text_label")
    val submitTextLabel: String?,

    @ColumnInfo(name = "subscribers")
    val subscribers: Int?,

    @ColumnInfo(name = "suggested_comment_sort")
    val suggestedCommentSort: CommentSort?,

    @ColumnInfo(name = "title")
    val title: String,
    val url: String,

    @ColumnInfo(name = "user_is_muted")
    val isUserMuted: Boolean = false,

    @ColumnInfo(name = "user_is_banned")
    val isUserBanned: Boolean = false,

    @ColumnInfo(name = "user_is_contributor")
    val isUserContributor: Boolean = false,

    @ColumnInfo(name = "user_is_moderator")
    val isUserModerator: Boolean = false,

    @ColumnInfo(name = "user_is_subscriber")
    val isUserSubscriber: Boolean = false,

    @ColumnInfo(name = "user_flair_text")
    val userFlairText: String?,

    @ColumnInfo(name = "user_flair_enabled_in_sr")
    val isUserFlairGenerallyEnabled: Boolean?,

    @Json(name = "user_sr_flair_enabled")
    val userFlairEnabled: Boolean?
)