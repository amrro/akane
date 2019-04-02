package app.akane.ui.feed

import android.view.View
import app.akane.*
import app.akane.data.entity.Post
import app.akane.data.entity.PostInfo
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController

class FeedEpoxyController
    (
    private val callbacks: Callback,
    private val feedbackOptions: FeedOptionsCallback
) : PagedListEpoxyController<Post>() {


    interface Callback {
        fun upvote(view: View, post: Post)
        fun downvote(post: Post)
        fun save(post: Post)
        fun hide(post: Post)
        fun moreOptions(view: View, info: PostInfo)
    }

    interface FeedOptionsCallback {
        fun onSortOptionClicked(view: View)
        fun onTimePeriodClicked(view: View)
    }

    @Suppress("UselessCallOnCollection")
    override fun addModels(models: List<EpoxyModel<*>>) {
        feedOptions {
            id(0)
            callback(feedbackOptions)
        }
        val filteredModels = models.filterNotNull()
        if (filteredModels.isNotEmpty()) super.addModels(models)
    }


    override fun buildItemModel(currentPosition: Int, item: Post?): EpoxyModel<*> {
        if (item == null) {
            return CardLinkBindingModel_()
                .id(currentPosition)
        }


        if (item.postInfo.isHidden) {
            return RemovedCardBindingModel_()
                .id(item.postInfo.id)
        }


        if (item.postInfo.isSelfPost && item.postInfo.selfText.isNotEmpty()) {
            return CardTextBindingModel_()
                .id(item.postInfo.id)
                .post(item)
                .callbacks(callbacks)
        }

        if (item.postInfo.postHint == "image") {
            return CardImageBindingModel_()
                .id(item.postInfo.id)
                .post(item)
                .callbacks(callbacks)
        }

        return CardLinkBindingModel_()
            .id(item.postInfo.id)
            .post(item)
            .callbacks(callbacks)
    }
}