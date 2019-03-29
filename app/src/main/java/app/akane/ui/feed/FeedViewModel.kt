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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import timber.log.Timber

class FeedViewModel @AssistedInject constructor(
    @Assisted val initialState: FeedViewState,
    private val repository: FeedRepository,
    private val dispatchers: AppCoroutineDispatchers
) : BaseMvRxViewModel<FeedViewState>(initialState, debugMode = false) {

    val snackbarMessage = SnackbarMessage()

    private val supervisor = SupervisorJob()
    private val exceptionHandler =
        CoroutineExceptionHandler { _, cause ->
            onError(cause)
        }

    private val scope = viewModelScope + supervisor + exceptionHandler


    internal fun setConfigs(
        name: String,
        sort: SubredditSort = SubredditSort.HOT,
        timePeriod: TimePeriod? = null
    ) {
        repository.updateConfigs(
            name.notNullOrEmpty { "FeedViewModel.setConfigs(name: $name): name of subreddit cannot be null!" },
            sort,
            timePeriod
        )

        // TODO: Move this line from here.
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


    /**
     * This method is responsible for firing or refreshing the list to get
     * user feed.
     */
    fun refresh() {
        setState { copy(isLoading = true) }
        safeRequest { repository.refresh() }
        setState { copy(isLoading = false) }
    }


    private fun onError(cause: Throwable) {
        runOnMain {
            //            if (cause is UnknownHostException) {
//                snackbarMessage.value = "Please Check Your connection!"
//            } else {
//                snackbarMessage.value = cause.message
//            }

            snackbarMessage.value = cause.message
            Timber.e(cause)
        }
    }

    private fun runOnMain(action: () -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            action()
        }
    }

    private fun safeRequest(block: suspend () -> Unit) {
        try {
            scope.launch(dispatchers.io) { block() }
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    internal fun sendMessage(text: String) {
        if (!text.isNotEmpty()) {
            snackbarMessage.value = text
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