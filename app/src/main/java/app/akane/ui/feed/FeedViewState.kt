package app.akane.ui.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.akane.data.entity.Post
import com.airbnb.mvrx.MvRxState

data class FeedViewState(
    val isLoading: Boolean = false,
    val feed: LiveData<PagedList<Post>>? = null,
    val isEmpty: Boolean = false
) : MvRxState