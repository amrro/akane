package app.akane

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import app.akane.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    @Inject
    lateinit var accountHelper: AccountHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUser()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)



        fab.setOnClickListener {
            onNewUserRequested()
        }

    }

    // Called when the FAB is clicked
    private fun onNewUserRequested() {
        startActivityForResult(Intent(this, NewUserActivity::class.java), REQ_CODE_LOGIN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // The user could have pressed the back button before authorizing our app, make sure we have
        // an authenticated user before starting the UserOverviewActivity.
        if (requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
            getUser()
        }

    }

    private fun getUser() {
        GlobalScope.launch(Dispatchers.IO) {
            val redditClient = accountHelper.reddit
            (application as AkaneApp).getAccountHelper().isAuthenticated()
            val me = redditClient.me().about()
            val about = redditClient.subreddit("gifs").about()
            val wiki = redditClient.subreddit("gifs").wiki()
            val comments = redditClient.submission("92dd8").comments()
            Log.d(":::: wiki", wiki.toString())
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    companion object {
        private const val REQ_CODE_LOGIN = 0
    }
}
