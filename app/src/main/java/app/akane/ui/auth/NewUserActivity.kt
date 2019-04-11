package app.akane.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.akane.R
import app.akane.util.AppCoroutineDispatchers
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This activity is dedicated to a WebView to guide the user through the authentication process.
 *
 * First, a StatefulAuthHelper is created by calling App.getAccountHelper().switchToNewUser(). We
 * pull data from/send data to this object during the authentication phase. When the user has been
 * authenticated or the user has denied our app access to their account, the activity finishes.
 */
class NewUserActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var redditManager: RedditManager

    @Inject
    lateinit var dispatchers: AppCoroutineDispatchers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        // TODO: Don't save any cookies, cache, or history from previous sessions. If we don't, once the
        // first user logs in and authenticates, the next time we go to add a new user, the first
        // user will be automatically logged in, which is not what we want.
        val webView = findViewById<WebView>(R.id.webView)
        webView.clearCache(true)
        webView.clearHistory()

        // Get a StatefulAuthHelper instance to manage interactive authentication
        val helper = redditManager.accountHelper.switchToNewUser()

        // Watch for pages loading
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                if (helper.isFinalRedirectUrl(url)) {

                    // No need to continue loading, we've already got all the required information
                    webView.stopLoading()
                    webView.visibility = View.GONE

                    // Try to authenticate the user
                    GlobalScope.launch(dispatchers.io) {
                        try {
                            helper.onUserChallenge(url)
                            setResult(Activity.RESULT_OK, Intent())
                        } catch (ex: Exception) {
                            Snackbar.make(window.decorView.rootView, ex.localizedMessage, Snackbar.LENGTH_LONG)
                                .show()
                            setResult(Activity.RESULT_CANCELED, Intent())
                        } finally {
                            finish()
                        }
                    }
                }
            }
        }

        // Generate an authentication URL
        val authUrl = helper.getAuthorizationUrl(true, true, *RedditManager.SCOPES)
        // Finally, show the authorization URL to the user
        webView.loadUrl(authUrl)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }
}
