package app.akane.ui.feed


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import app.akane.R
import app.akane.data.entity.Post
import app.akane.data.entity.PostInfo
import app.akane.databinding.FragmentSubmissionsListBinding
import app.akane.util.BaseMvRxFragment
import app.akane.util.SnackbarMessage
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import javax.inject.Inject

class FeedListFragment : BaseMvRxFragment() {

    @Inject
    lateinit var feedViewModelFactory: FeedViewModel.Factory

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSubmissionsListBinding
    private lateinit var controller: FeedEpoxyController
    private val feedViewModel: FeedViewModel by fragmentViewModel()
    private lateinit var actionsViewModel: ActionsViewModel

    private val subredditName: String by lazy {
        requireArguments().getString(KEY_SUBREDDIT_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller =
            FeedEpoxyController(object : FeedEpoxyController.Callback {
                override fun upvote(view: View, post: Post) {
                    actionsViewModel.upvote(post.postInfo.id)
                }

                override fun downvote(post: Post) {
                    actionsViewModel.downvote(post.postInfo.id)
                }


                override fun save(post: Post) {
//                    actionsViewModel.save(post.postInfo.id)
                }

                override fun hide(post: Post) {
//                    actionsViewModel.hide(post.postInfo.id)
                }

                override fun moreOptions(view: View, info: PostInfo) {
                    val moreOptionsPopMenu = PopupMenu(context, view)
                    moreOptionsPopMenu.menuInflater.inflate(
                        R.menu.menu_card_more_options,
                        moreOptionsPopMenu.menu
                    )
                    moreOptionsPopMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.action_save_post -> {
                                actionsViewModel.save(info)
                            }
                            R.id.action_hide_post -> {
                                actionsViewModel.hide(info)
                            }
                            R.id.action_open_in_browser -> {
                                actionsViewModel.openInBrowser(info)
                            }
                            R.id.action_copy_link -> {
                                actionsViewModel.copyPostLink(info)
                            }
                            R.id.action_block_user -> {
                                actionsViewModel.blockUser(info)
                            }
                            else -> {
                            }
                        }

                        true
                    }
                    moreOptionsPopMenu.show()
                }
            },
                feedbackOptions = object : FeedEpoxyController.FeedOptionsCallback {
                    override fun onSortOptionClicked(view: View) {
                        showFeedOptions(view as MaterialButton)
                    }

                    override fun onTimePeriodClicked(view: View) {

                    }
                })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_submissions_list,
            container,
            false
        )
        binding.feedRecyclerview.setController(controller)
        return binding.root
    }

    fun showFeedOptions(button: MaterialButton) {
        PopupMenu(context, button, Gravity.BOTTOM).apply {
            this.menuInflater.inflate(
                R.menu.menu_sort_type,
                this.menu
            )
            this.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_feed_sort_best -> {
                        onFeedConfigurationChanged(SubredditSort.BEST)
                        button.text = "Best"
                    }
                    R.id.action_feed_sort_hot -> {
                        onFeedConfigurationChanged(SubredditSort.HOT)
                        button.text = "Hot"
                    }
                    R.id.action_feed_sort_new -> {
                        onFeedConfigurationChanged(SubredditSort.NEW)
                        button.text = "New"
                    }

                    // TOP
                    R.id.action_feed_sort_top_hour -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.HOUR)
                        button.text = "Top (hour)"
                    }
                    R.id.action_feed_sort_top_24_hour -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.DAY)
                        button.text = "Top (24 Hour)"
                    }
                    R.id.action_feed_sort_top_week -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.WEEK)
                        button.text = "Top (Week)"
                    }
                    R.id.action_feed_sort_top_month -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.MONTH)
                        button.text = "Top (Month)"
                    }
                    R.id.action_feed_sort_top_year -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.YEAR)
                        button.text = "Top (Year)"
                    }
                    R.id.action_feed_sort_top_all_time -> {
                        onFeedConfigurationChanged(SubredditSort.TOP, TimePeriod.ALL)
                        button.text = "Top (All Time)"
                    }

                    // CONTROVERSIAL.
                    R.id.action_feed_sort_controversial_hour -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.HOUR)
                        button.text = "Controversial (hour)"
                    }
                    R.id.action_feed_sort_controversial_24_hour -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.DAY)
                        button.text = "Controversial (24 Hour)"
                    }
                    R.id.action_feed_sort_controversial_week -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.WEEK)
                        button.text = "Controversial (Week)"
                    }
                    R.id.action_feed_sort_controversial_month -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.MONTH)
                        button.text = "Controversial (Month)"
                    }
                    R.id.action_feed_sort_controversial_year -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.YEAR)
                        button.text = "Controversial (Year)"
                    }
                    R.id.action_feed_sort_controversial_all_time -> {
                        onFeedConfigurationChanged(SubredditSort.CONTROVERSIAL, TimePeriod.ALL)
                        button.text = "Controversial (All Time)"
                    }
                    else -> false
                }
                true
            }

            this.show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ActionsViewModel::class.java)

        onFeedConfigurationChanged()
        setupSnackbar()
        binding.feedSwipeRefresh.setOnRefreshListener(feedViewModel::refresh)
    }

    private fun onFeedConfigurationChanged(
        sort: SubredditSort = SubredditSort.HOT,
        timePeriod: TimePeriod? = null
    ) {
        feedViewModel.setConfigs(
            name = subredditName,
            sort = sort,
            timePeriod = timePeriod
        )
    }


    override fun invalidate() {
        withState(feedViewModel) { state ->
            binding.state = state
            // PagingEpoxyController does not like being updated before it has a list
            state.feed?.observe(this, Observer(controller::submitList))
        }
    }

    private val messageObserver = object : SnackbarMessage.SnackbarObserver {
        override fun onNewMessage(snackbarMessageResourceId: String) {
            view?.let {
                Snackbar.make(it, snackbarMessageResourceId, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setupSnackbar() {
        feedViewModel.snackbarMessage.observe(this, messageObserver)
        actionsViewModel.snackbarMessage.observe(this, messageObserver)
    }


    companion object {
        private const val KEY_SUBREDDIT_NAME = "key-subreddit-name"

        fun newInstance(subredditName: String): FeedListFragment {
            require(subredditName.isNotEmpty()) {
                "FeedFragment.newInstance(subredditName: $subredditName): subredditName cannout be empty."
            }

            return FeedListFragment().apply {
                arguments = bundleOf(KEY_SUBREDDIT_NAME to subredditName)
            }
        }
    }
}
