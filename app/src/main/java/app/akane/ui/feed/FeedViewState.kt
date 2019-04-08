package app.akane.ui.feed

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import app.akane.data.entity.Post
import com.airbnb.mvrx.MvRxState

data class FeedViewState(
//    val subredditName: String,
    val isLoading: Boolean = false,
    val feed: LiveData<PagedList<Post>>? = null,
    val isEmpty: Boolean = false
//    val sorting: SubredditSort = SubredditSort.HOT,
//    val timePeriod: TimePeriod? = null
) : MvRxState