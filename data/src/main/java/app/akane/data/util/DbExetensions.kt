package app.akane.data.util

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.Date

fun Date.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(DateTimeUtils.toInstant(this), ZoneId.systemDefault())
