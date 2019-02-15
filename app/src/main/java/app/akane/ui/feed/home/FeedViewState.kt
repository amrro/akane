package app.akane.ui.feed.home

import androidx.paging.PagedList
import com.airbnb.mvrx.MvRxState
import net.dean.jraw.models.Submission

data class FeedViewState(
        val isLoading: Boolean = false,
        val homeFeed: PagedList<Submission>? = null,
        val isEmpty: Boolean = false
) : MvRxState