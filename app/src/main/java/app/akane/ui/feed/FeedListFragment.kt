package app.akane.ui.feed


import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import app.akane.data.entity.Post
import app.akane.databinding.FragmentSubmissionsListBinding
import app.akane.util.BaseMvRxFragment
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.VoteDirection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedListFragment : BaseMvRxFragment() {

    @Inject
    lateinit var feedViewModelFactory: FeedViewModel.Factory

    private lateinit var binding: FragmentSubmissionsListBinding
    private lateinit var controller: FeedEpoxyController
    private val feedViewModel: FeedViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller =
            FeedEpoxyController(object : FeedEpoxyController.Callback {
                override fun upVote(view: View, post: Post) {
                    feedViewModel.vote(post, VoteDirection.UP)
                    (view as ImageButton).drawable.let {
                        if (it is Animatable) {
                            it.start()
                        }
                    }
                }

                override fun downVote(view: View, post: Post) {
                    feedViewModel.vote(post, VoteDirection.DOWN)
                }


                override fun save(post: Post) {
                    feedViewModel.saveSubmission(post)
                }

                override fun hide(post: Post) {
                    feedViewModel.hideSubmission(post)
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
        feedViewModel.setConfigs(
            name = arguments?.getString(KEY_SUBREDDIT_NAME),
            sort = SubredditSort.BEST
        )
        setupSnackbar()
        binding.feedSwipeRefresh.setOnRefreshListener(feedViewModel::refresh)
    }

    override fun invalidate() {
        withState(feedViewModel) { state ->
            binding.state = state
            // PagingEpoxyController does not like being updated before it has a list
            state.feed?.observe(this, Observer(controller::submitList))
        }
    }

    private fun setupSnackbar() {
        feedViewModel.snackbarMessage.observe(this, object : SnackbarMessage.SnackbarObserver {
            override fun onNewMessage(snackbarMessageResourceId: String) {
                view?.let {
                    Snackbar.make(it, snackbarMessageResourceId, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
    }


    companion object {

        private const val KEY_SUBREDDIT_NAME = "key-subreddit-name"

        fun newInstance(subredditName: String): FeedListFragment {
            require(subredditName.isNotEmpty()) {
                "FeedFragment.newInstance(subredditName: $subredditName): subredditName cannout be empty."
            }

            return FeedListFragment().apply {
                arguments = Bundle().apply { putString(KEY_SUBREDDIT_NAME, subredditName) }
            }
        }
    }

}
