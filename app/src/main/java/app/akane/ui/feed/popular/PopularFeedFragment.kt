package app.akane.ui.feed.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import app.akane.databinding.FragmentSubmissionsListBinding
import app.akane.ui.feed.PageViewModel
import app.akane.util.BaseMvRxFragment
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class PopularFeedFragment : BaseMvRxFragment() {

    @Inject
    lateinit var popularFeedViewModelFactory: PopularFeedViewModel.Factory

    private lateinit var binding: FragmentSubmissionsListBinding
    private lateinit var pageViewModel: PageViewModel
    private lateinit var controller: SubmissionsFeedController
    private val popularFeedViewModel: PopularFeedViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        controller = SubmissionsFeedController(object : SubmissionsFeedController.Callback {
            override fun vote(submission: Submission, dir: VoteDirection) {
                popularFeedViewModel.vote(submission, dir)
            }

            override fun save(submission: Submission) {
                popularFeedViewModel.saveSubmission(submission)
            }

            override fun hide(submission: Submission) {
                popularFeedViewModel.hideSubmission(submission)
            }
        })

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmissionsListBinding.inflate(inflater, container, false)
        binding.feedRecyclerview.setController(controller)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar()

    }

    override fun invalidate() {
        withState(popularFeedViewModel) { state ->
            if (state.popularFeed != null) {
                // PagingEpoxyController does not like being updated before it has a list
                controller.submitList(state.popularFeed)
            }
        }
    }

    private fun setupSnackbar() {
        popularFeedViewModel.snackbarMessage.observe(this, object : SnackbarMessage.SnackbarObserver {
            override fun onNewMessage(snackbarMessageResourceId: String) {
                view?.let {
                    Snackbar.make(it, snackbarMessageResourceId, Snackbar.LENGTH_LONG)
                            .show()
                }
            }
        })
    }
}