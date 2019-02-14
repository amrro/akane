package app.akane.ui.feed.popular

import app.akane.CardLinkBindingModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection

class SubmissionsFeedController(
        val callbacks: Callback
) : PagedListEpoxyController<Submission>() {


    public interface Callback {
        fun vote(submission: Submission, dir: VoteDirection)
        fun save(submission: Submission, save: Boolean)
        fun hide(submission: Submission, hide: Boolean)
    }

    override fun buildItemModel(currentPosition: Int, item: Submission?): EpoxyModel<*> {
        if (item == null) {
            return CardLinkBindingModel_()
                    .id(currentPosition)
                    .callbacks(callbacks)
        }

        return CardLinkBindingModel_()
                .id(item.id)
                .submission(item)
                .callbacks(callbacks)

    }
}