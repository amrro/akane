package app.akane.ui.feed.popular

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import app.akane.repo.PopularFeedDataSource
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dean.jraw.ApiException
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection
import net.dean.jraw.oauth.AccountHelper

class PopularFeedViewModel @AssistedInject constructor(
        @Assisted val initialState: PopularViewState,
        val accountHelper: AccountHelper,
        factory: PopularFeedDataSource.Factory
) : BaseMvRxViewModel<PopularViewState>(initialState, debugMode = true) {

    private val reddit = accountHelper.reddit
    private val pagedListObservable: Observable<PagedList<Submission>>
    val snackbarMessage = SnackbarMessage()

    init {
        pagedListObservable = pagedList(factory)
        refresh()
    }

    private fun pagedList(factory: PopularFeedDataSource.Factory): Observable<PagedList<Submission>> {
        return RxPagedListBuilder<Int, Submission>(factory, PAGIN_CONFIG)
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
            }
        }

    }

    fun saveSubmission(submission: Submission) {
        try {
            val submissionReference = submission.toReference(reddit)
            submissionReference.setSaved(!submissionReference.inspect().isSaved)
        } catch (e: ApiException) {
            snackbarMessage.value = e.explanation
        }
    }

    fun refresh() {
        pagedListObservable.execute { copy(popularFeed = it()) }
    }


    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: PopularViewState): PopularFeedViewModel
    }


    companion object : MvRxViewModelFactory<PopularFeedViewModel, PopularViewState> {
        private val PAGIN_CONFIG = PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()

        override fun create(
                viewModelContext: ViewModelContext,
                state: PopularViewState
        ): PopularFeedViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<PopularFeedFragment>()
            return fragment.popularFeedViewModelFactory.create(state)
        }
    }
}