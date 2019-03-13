package app.akane.util

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(DateTimeUtils.toInstant(this), ZoneId.systemDefault())