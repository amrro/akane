package app.akane.binding

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.akane.R
import app.akane.data.entity.Post
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import net.dean.jraw.models.VoteDirection
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import kotlin.math.roundToInt

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("frescoUri")
    fun frescoImage(frescoImage: SimpleDraweeView, post: Post) {
        post.images.lastOrNull()?.let { preview ->
            frescoImage.layoutParams.height =
                (preview.height * (frescoImage.width.toFloat() / preview.width.toFloat())).roundToInt()

            val controller = Fresco.newDraweeControllerBuilder()
                .setUri(preview.link)
                .setAutoPlayAnimations(true)
                .build()

            val progressBarDrawable = ProgressBarDrawable().apply {
                this.color = Color.RED
                this.backgroundColor = app.akane.R.color.colorPrimary
                this.setPadding(0)
                this.barWidth = 5
            }

            val hierarchy = GenericDraweeHierarchyBuilder(frescoImage.context.resources)
                .setPlaceholderImage(app.akane.R.drawable.ic_baseline_photo_24px)
                .setProgressBarImage(progressBarDrawable)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()

            frescoImage.apply {
                layoutParams.height =
                    (preview.height * (frescoImage.width.toFloat() / preview.width.toFloat())).roundToInt()
                this.controller = controller
                this.hierarchy = hierarchy
                this.setImageURI(preview.link)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("prettyNumber")
    fun prettifyNumbers(textView: TextView, score: Int) {
        when {
            score == 0 -> textView.text = "Vote"
            score > 1000 -> textView.text = String.format("%.1fK", score.toDouble() / 1000)
            else -> textView.text = score.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("vote")
    fun setVote(imageButton: ImageButton, vote: VoteDirection) {
        imageButton.isSelected = false

        if (imageButton.tag == "upvote" && vote == VoteDirection.UP) {
            imageButton.isSelected = true
        }

        if (imageButton.tag == "downvote" && vote == VoteDirection.DOWN) {
            imageButton.isSelected = true
        }
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
            textView.text = "· ${years}y"
            return
        }

        val months = fromTemp.until(to, ChronoUnit.MONTHS)
        if (months > 1) {
            textView.text = "· ${months}m"
            return
        }

        val days = fromTemp.until(to, ChronoUnit.DAYS)
        if (days > 1) {
            textView.text = "· ${days}d"
            return
        }

        val hours = fromTemp.until(to, ChronoUnit.HOURS)
        if (hours > 1) {
            textView.text = "· ${hours}h"
            return
        }

        val minutes = fromTemp.until(to, ChronoUnit.MINUTES)

        if (minutes > 1) {
            textView.text = "· $minutes mins"
            return
        }
        val seconds = fromTemp.until(to, ChronoUnit.SECONDS)

        if (seconds > 1) {
            textView.text = "· ${seconds}s"
            return
        }
    }
}
