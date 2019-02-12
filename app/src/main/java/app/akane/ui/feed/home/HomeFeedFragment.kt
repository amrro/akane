package app.akane.ui.feed.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.toLiveData
import app.akane.databinding.FragmentSubmissionsListBinding
import app.akane.di.Injectable
import app.akane.repo.HomeFeedDataSource
import app.akane.ui.feed.PageViewModel
import app.akane.ui.feed.popular.SubmissionsFeedController
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFeedFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentSubmissionsListBinding
    private lateinit var pageViewModel: PageViewModel
    private lateinit var controller: SubmissionsFeedController


    @Inject
    lateinit var accountHelper: AccountHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        controller = SubmissionsFeedController()
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

        val ds = HomeFeedDataSource.Factory(accountHelper)

        ds.toLiveData(pageSize = 20).observe(this, Observer {
            controller.submitList(it)
        })

    }



}
