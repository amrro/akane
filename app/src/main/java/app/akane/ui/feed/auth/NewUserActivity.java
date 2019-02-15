package app.akane.ui.feed.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import net.dean.jraw.oauth.OAuthException;
import net.dean.jraw.oauth.StatefulAuthHelper;

import java.lang.ref.WeakReference;

import app.akane.AkaneApp;
import app.akane.R;

/**
 * This activity is dedicated to a WebView to guide the user through the authentication process.
 *
 * First, a StatefulAuthHelper is created by calling App.getAccountHelper().switchToNewUser(). We
 * pull data from/send data to this object during the authentication phase. When the user has been
 * authenticated or the user has denied our app access to their account, the activity finishes.
 */
public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        // TODO: Don't save any cookies, cache, or history from previous sessions. If we don't, once the
        // first user logs in and authenticates, the next time we go to add a new user, the first
        // user will be automatically logged in, which is not what we want.
        final WebView webView = findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();

        // Get a StatefulAuthHelper instance to manage interactive authentication
        final StatefulAuthHelper helper = ( (AkaneApp) getApplication()).getAccountHelper().switchToNewUser();

        // Watch for pages loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (helper.isFinalRedirectUrl(url)) {
                    // No need to continue loading, we've already got all the required information
                    webView.stopLoading();
                    webView.setVisibility(View.GONE);

                    // Try to authenticate the user
                    new AuthenticateTask(NewUserActivity.this, helper).execute(url);
                }
            }
        });

        // Generate an authentication URL
        String[] scopes = new String[]{ "read", "identity", "vote" };
        String authUrl = helper.getAuthorizationUrl(true, true, scopes);

        // Finally, show the authorization URL to the user
        webView.loadUrl(authUrl);
    }

    /**
     * An async task that takes a final redirect URL as a parameter and reports the success of
     * authorizing the user.
     */
    private static final class AuthenticateTask extends AsyncTask<String, Void, Boolean> {
        // Use a WeakReference so that we don't leak a Context
        private final WeakReference<Activity> context;

        private final StatefulAuthHelper helper;

        AuthenticateTask(Activity context, StatefulAuthHelper helper) {
            this.context = new WeakReference<>(context);
            this.helper = helper;
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                helper.onUserChallenge(urls[0]);
                return true;
            } catch (OAuthException e) {
                // Report failure if an OAuthException occurs
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            // Finish the activity if it's still running
            Activity host = this.context.get();
            if (host != null) {
                host.setResult(success ? Activity.RESULT_OK : Activity.RESULT_CANCELED, new Intent());
                host.finish();
            }
        }
    }
}
