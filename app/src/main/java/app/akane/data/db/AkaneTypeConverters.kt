package app.akane.data.db

import androidx.room.TypeConverter
import net.dean.jraw.models.CommentSort
import net.dean.jraw.models.DistinguishedStatus
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
    fun fromCommentSort(commentSort: CommentSort?) = commentSort?.let { it.toString() }

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

}