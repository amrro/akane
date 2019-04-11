package app.akane.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.Date
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun Date.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(DateTimeUtils.toInstant(this), ZoneId.systemDefault())

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun CoroutineScope.safeRequest(
    context: CoroutineContext = EmptyCoroutineContext,
    onError: (Exception) -> Unit,
    block: suspend () -> Unit
) {
    val newContext = newCoroutineContext(context)
    try {
        launch(newContext) { block() }
    } catch (ex: Exception) {
        onError(ex)
    }
}