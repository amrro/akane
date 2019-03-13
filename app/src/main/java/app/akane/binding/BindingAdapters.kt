package app.akane.binding

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalDateTime

import org.threeten.bp.temporal.ChronoUnit


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
    fun setSubredditName(textView: TextView, subredditName: String?) {
        subredditName?.let {
            val text = "r/$it"
            textView.text = styleText(text)
        }
    }

    @JvmStatic
    @BindingAdapter("username")
    fun setUsername(textView: TextView, username: String?) {
        username?.let {
            val text = "u/$it"
            textView.text = styleText(text)
        }
    }

    @JvmStatic
    fun styleText(text: String) = SpannableStringBuilder(text).apply {
        setSpan(
            ForegroundColorSpan(app.akane.R.color.colorPrimary),
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
    fun since(textView: TextView, date: LocalDateTime?) {
        if (date == null) return

        val fromTemp = LocalDateTime.from(date)
        val to = LocalDateTime.now()

        val years = fromTemp.until(to, ChronoUnit.YEARS)
        if (years > 1) {
            textView.text = "${years}y"
            return
        }

        val months = fromTemp.until(to, ChronoUnit.MONTHS)
        if (months > 1) {
            textView.text = "${months}m"
            return
        }

        val days = fromTemp.until(to, ChronoUnit.DAYS)
        if (days > 1) {
            textView.text = "${days}d"
            return
        }

        val hours = fromTemp.until(to, ChronoUnit.HOURS)
        if (hours > 1) {
            textView.text = "${hours}h"
            return
        }

        val minutes = fromTemp.until(to, ChronoUnit.MINUTES)

        if (minutes > 1) {
            textView.text = "$minutes mins"
            return
        }
        val seconds = fromTemp.until(to, ChronoUnit.SECONDS)

        if (seconds > 1) {
            textView.text = "${seconds}s"
            return
        }
    }

}
