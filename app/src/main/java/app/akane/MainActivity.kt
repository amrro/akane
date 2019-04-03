package app.akane

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.akane.databinding.ActivityMainBinding
import app.akane.ui.auth.NewUserActivity
import app.akane.ui.feed.HomePagerAdapter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.mainToolbar)
        binding.viewPager.run {
            adapter = HomePagerAdapter(this@MainActivity, supportFragmentManager)
            binding.tabs.setupWithViewPager(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_log_in -> onNewUserRequested()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun onNewUserRequested() {
        startActivityForResult(Intent(this, NewUserActivity::class.java), REQ_CODE_LOGIN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // The user could have pressed the back button before authorizing our app, make sure we have
        // an authenticated user before starting the UserOverviewActivity.
        if (requestCode == REQ_CODE_LOGIN && resultCode == RESULT_OK) {
            // TODO: handle that.
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {
        private const val REQ_CODE_LOGIN = 0
    }
}
