package app.akane.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import app.akane.data.entity.Post
import app.akane.data.repo.feed.FeedRepository
import app.akane.util.AppCoroutineDispatchers
import app.akane.util.SnackbarMessage
import app.akane.util.notNullOrEmpty
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dean.jraw.ApiException
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import net.dean.jraw.models.VoteDirection
import net.dean.jraw.oauth.AccountHelper
import timber.log.Timber

class FeedViewModel @AssistedInject constructor(
    @Assisted val initialState: FeedViewState,
    val accountHelper: AccountHelper,
    val repository: FeedRepository,
    val dispatchers: AppCoroutineDispatchers
) : BaseMvRxViewModel<FeedViewState>(initialState, debugMode = false) {

    private val reddit = if (accountHelper.isAuthenticated()) accountHelper.reddit else null
    val snackbarMessage = SnackbarMessage()

    internal fun setConfigs(
        name: String?,
        sort: SubredditSort = SubredditSort.HOT,
        timePeriod: TimePeriod? = null
    ) {
        repository.updateConfigs(
            name.notNullOrEmpty { "FeedViewModel.setConfigs(name: $name): name of subreddit cannot be null!" },
            sort,
            timePeriod
        )
        setState { copy(feed = pagedList()) }
        refresh()
    }

    private fun pagedList(): LiveData<PagedList<Post>> {
        return repository.observeForPaging(
            object : PagedList.BoundaryCallback<Post>() {
                override fun onItemAtEndLoaded(itemAtEnd: Post) {
                    viewModelScope.launch(dispatchers.io) {
                        repository.loadMore()
                    }
                }
            })
    }

    fun vote(post: Post, dir: VoteDirection) = viewModelScope.launch(dispatchers.io) {
        try {
            if (reddit != null) {
                val subRef = reddit.submission(post.id)
                subRef.setVote(
                    if (subRef.inspect().vote == dir) VoteDirection.NONE
                    else dir
                )
            }
        } catch (e: ApiException) {
            runOnMain {
                snackbarMessage.value = e.explanation
                Timber.e(e)
            }
        }
    }


    fun hideSubmission(post: Post) = viewModelScope.launch(dispatchers.io) {
        if (reddit != null) {
            try {
                val subRef = reddit.submission(post.id)
                subRef.setHidden(!subRef.inspect().isHidden)
            } catch (e: ApiException) {
                runOnMain {
                    snackbarMessage.value = e.explanation
                    Timber.e(e)
                }
            }
        }
    }


    fun saveSubmission(post: Post) = viewModelScope.launch(dispatchers.io) {
        if (reddit != null) {
            try {
                val submissionReference = reddit.submission(post.id)
                submissionReference.setSaved(!submissionReference.inspect().isSaved)
            } catch (e: ApiException) {
                runOnMain {
                    snackbarMessage.value = e.explanation
                    Timber.e(e)
                }
            }
        }
    }


    /**
     * This method is responsible for firing or refreshing the list to get
     * user feed.
     */
    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            setState { copy(isLoading = true) }
            repository.refresh()
            setState { copy(isLoading = false) }
        }
    }

    private suspend fun <T> runOnMain(action: () -> T): T {
        return withContext(dispatchers.main) {
            return@withContext action()
        }
    }


    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: FeedViewState): FeedViewModel
    }

    companion object : MvRxViewModelFactory<FeedViewModel, FeedViewState> {

        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: FeedViewState
        ): FeedViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<FeedListFragment>()
            return fragment.feedViewModelFactory.create(state)
        }
    }
}