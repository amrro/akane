package app.akane.ui.feed.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.akane.databinding.FragmentSubmissionsListBinding
import app.akane.ui.feed.popular.SubmissionsFeedController
import app.akane.util.BaseMvRxFragment
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFeedFragment : BaseMvRxFragment() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModel.Factory

    private lateinit var binding: FragmentSubmissionsListBinding
    private lateinit var controller: SubmissionsFeedController
    private val homeViewModel: HomeViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = SubmissionsFeedController(object : SubmissionsFeedController.Callback {
            override fun vote(submission: Submission, dir: VoteDirection) {
                homeViewModel.vote(submission, dir)
            }

            override fun save(submission: Submission) {
                homeViewModel.saveSubmission(submission)
            }

            override fun hide(submission: Submission) {
                homeViewModel.hideSubmission(submission)
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
        withState(homeViewModel) { state ->
            if (state.homeFeed != null) {
                // PagingEpoxyController does not like being updated before it has a list
                controller.submitList(state.homeFeed)
            }
        }
    }

    private fun setupSnackbar() {
        homeViewModel.snackbarMessage.observe(this, object : SnackbarMessage.SnackbarObserver {
            override fun onNewMessage(snackbarMessageResourceId: String) {
                view?.let {
                    Snackbar.make(it, snackbarMessageResourceId, Snackbar.LENGTH_LONG)
                            .show()
                }
            }
        })
    }

}
