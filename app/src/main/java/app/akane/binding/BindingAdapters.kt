package app.akane.binding

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.akane.R

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

}
