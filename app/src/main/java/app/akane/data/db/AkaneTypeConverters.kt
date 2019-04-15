package app.akane.data.db

import androidx.room.TypeConverter
import app.akane.data.entity.ImagePreview
import net.dean.jraw.models.CommentSort
import net.dean.jraw.models.DistinguishedStatus
import net.dean.jraw.models.Subreddit
import net.dean.jraw.models.VoteDirection
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object AkaneTypeConverters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?) = value?.let { formatter.parse(value, LocalDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDateTime?): String? = date?.format(formatter)

    @TypeConverter
    @JvmStatic
    fun fromCommentSort(commentSort: CommentSort?) = commentSort?.toString()

    @TypeConverter
    @JvmStatic
    fun toCommentSort(sort: String?) = sort?.let { CommentSort.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun fromDistinguishedStatus(distinguishedStatus: DistinguishedStatus): String {
        return distinguishedStatus.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toDistinguishedStatus(status: String): DistinguishedStatus {
        return DistinguishedStatus.valueOf(status)
    }

    // net.dean.jraw.models.VoteDirection
    @TypeConverter
    @JvmStatic
    fun toVoteDirection(vote: String) = VoteDirection.valueOf(vote)

    @TypeConverter
    @JvmStatic
    fun fromVoteDirection(vote: VoteDirection) = vote.toString()

    @TypeConverter
    @JvmStatic
    fun toImageType(type: String) = ImagePreview.ImageType.valueOf(type)

    @TypeConverter
    @JvmStatic
    fun fromImageType(type: ImagePreview.ImageType) = type.toString()

    @TypeConverter
    @JvmStatic
    fun fromSubmissionType(type: Subreddit.SubmissionType?) = type?.toString()

    @TypeConverter
    @JvmStatic
    fun toSubmissionType(sort: String?) = sort?.let { Subreddit.SubmissionType.valueOf(it) }
}