package app.akane.binding

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.akane.R
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import java.util.*

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("subreddit")
    fun setSubredditName(textView: TextView, subredditName: String) {
        val text = "r/$subredditName"
        textView.text = styleText(text)
    }

    @JvmStatic
    @BindingAdapter("username")
    fun setUsername(textView: TextView, username: String) {
        val text = "u/$username"
        textView.text = styleText(text)
    }

    @JvmStatic
    fun styleText(text: String) = SpannableStringBuilder(text).apply {
        setSpan(
                ForegroundColorSpan(R.color.colorPrimary),
                0, 2,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        setSpan(
                TypefaceSpan("josefin_sans.xml"),
                0, 2,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }


    /**
     * TODO: This method is needed to be tested and optimized to be more accurate.
     */
    @JvmStatic
    @BindingAdapter("since")
    fun since(textView: TextView, date: Date) {
        val submissionInstant = Instant.ofEpochMilli(date.time)
        val duration = Duration.between(submissionInstant, Instant.now())

        val diff = when {
            duration.toDays() > 0L -> "${duration.toDays()}d"
            duration.toHours() > 0L -> "${duration.toHours()}h"
            duration.toMinutes() > 0L -> "${duration.toMinutes()}m"
            else -> "${duration.seconds}S"
        }

        textView.text = diff
    }

}
