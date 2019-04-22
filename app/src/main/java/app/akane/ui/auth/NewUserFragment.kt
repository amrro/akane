package app.akane.ui.auth

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.akane.R
import app.akane.data.RedditManager
import app.akane.di.Injectable
import app.akane.util.AppCoroutineDispatchers
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */

class NewUserFragment : Fragment(), Injectable {
    @Inject
    lateinit var redditManager: RedditManager

    @Inject
    lateinit var dispatchers: AppCoroutineDispatchers

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Don't save any cookies, cache, or history from previous sessions. If we don't, once the
        // first user logs in and authenticates, the next time we go to add a new user, the first
        // user will be automatically logged in, which is not what we want.
        val webView = view.findViewById<WebView>(R.id.webView)
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
                            findNavController().navigate(R.id.action_newUserFragment_to_homeFragment)
                        } catch (ex: Exception) {
                            Snackbar.make(view, ex.localizedMessage ?: "Some error happens!", Snackbar.LENGTH_LONG)
                                .show()
                            findNavController().navigateUp()
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
}
