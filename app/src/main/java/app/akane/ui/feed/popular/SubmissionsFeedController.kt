package app.akane.ui.feed.popular

import app.akane.CardLinkBindingModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import net.dean.jraw.models.Submission

class SubmissionsFeedController : PagedListEpoxyController<Submission>() {

    override fun buildItemModel(currentPosition: Int, item: Submission?): EpoxyModel<*> {
        if (item == null) {
            return CardLinkBindingModel_()
                    .id(currentPosition)
        }

        return CardLinkBindingModel_()
                .id(item.id)
                .submission(item)

    }
}