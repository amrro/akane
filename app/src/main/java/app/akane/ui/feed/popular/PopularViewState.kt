package app.akane.ui.feed.popular

import androidx.paging.PagedList
import com.airbnb.mvrx.MvRxState
import net.dean.jraw.models.Submission

data class PopularViewState(
        val isLoading: Boolean = false,
        val popularFeed: PagedList<Submission>? = null,
        val isEmpty: Boolean = false
) : MvRxState