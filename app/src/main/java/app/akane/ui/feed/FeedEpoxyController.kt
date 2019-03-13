package app.akane.ui.feed

import android.view.View
import app.akane.CardLinkBindingModel_
import app.akane.data.entity.Post
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController

class FeedEpoxyController
    (
    val callbacks: Callback
) : PagedListEpoxyController<Post>() {


    interface Callback {
        fun upVote(view: View, post: Post)
        fun downVote(view: View, post: Post)
        fun save(post: Post)
        fun hide(post: Post)
    }

    @Suppress("UselessCallOnCollection")
    override fun addModels(models: List<EpoxyModel<*>>) {
        val filteredModels = models.filterNotNull()
        if (filteredModels.isNotEmpty()) super.addModels(models)

    }

    override fun buildItemModel(currentPosition: Int, item: Post?): EpoxyModel<*> {
        if (item == null) {
            return CardLinkBindingModel_()
                .id(currentPosition)

        }

        return CardLinkBindingModel_()
            .id(item.id)
            .post(item)
            .callbacks(callbacks)
    }
}