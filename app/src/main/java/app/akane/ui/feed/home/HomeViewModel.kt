package app.akane.ui.feed.home

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import app.akane.repo.HomeFeedDataSource
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dean.jraw.ApiException
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection
import net.dean.jraw.oauth.AccountHelper
import timber.log.Timber

class HomeViewModel @AssistedInject constructor(
        @Assisted val initialState: FeedViewState,
        val accountHelper: AccountHelper,
        val factory: HomeFeedDataSource.Factory
) : BaseMvRxViewModel<FeedViewState>(initialState, debugMode = false) {

    private val reddit = accountHelper.reddit
    val snackbarMessage = SnackbarMessage()
    private val pagedListDisposal: Observable<PagedList<Submission>>

    init {
        pagedListDisposal = pagedList(factory)
        refresh()
    }

    private fun pagedList(factory: HomeFeedDataSource.Factory): Observable<PagedList<Submission>> {
        return RxPagedListBuilder(factory, PAGING_CONFIG)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Submission>() {
                    override fun onZeroItemsLoaded() {
                        setState { copy(isEmpty = true) }
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Submission) {
                        setState { copy(isEmpty = false) }
                    }

                    override fun onItemAtFrontLoaded(itemAtFront: Submission) {
                        setState { copy(isEmpty = false) }
                    }
                })
                .setFetchScheduler(Schedulers.io())
                .setNotifyScheduler(AndroidSchedulers.mainThread())
                .buildObservable()
    }

    fun vote(submission: Submission, dir: VoteDirection) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val subRef = submission.toReference(reddit)
                subRef.setVote(
                        if (subRef.inspect().vote == dir) VoteDirection.NONE
                        else dir
                )
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
                Timber.e(e)
            }
        }
    }

    fun hideSubmission(submission: Submission) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val subRef = submission.toReference(reddit)
                subRef.setHidden(!subRef.inspect().isHidden)
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
                Timber.e(e)
            }
        }
    }

    fun saveSubmission(submission: Submission) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val submissionReference = submission.toReference(reddit)
                submissionReference.setSaved(!submissionReference.inspect().isSaved)
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
                Timber.e(e)
            }
        }
    }

    /**
     * This method is responsible for firing or refreshing the list to get
     * user feed.
     */
    fun refresh() {
        setState { copy(isLoading = true) }
        // TODO: turn the `debugMode` one and guard the immutability of the state.
        pagedListDisposal.execute { copy(homeFeed = it(), isLoading = false) }
    }


    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: FeedViewState): HomeViewModel
    }

    companion object : MvRxViewModelFactory<HomeViewModel, FeedViewState> {
        private val PAGING_CONFIG = PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()


        @JvmStatic
        override fun create(
                viewModelContext: ViewModelContext,
                state: FeedViewState
        ): HomeViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<HomeFeedFragment>()
            return fragment.homeViewModelFactory.create(state)
        }
    }
}