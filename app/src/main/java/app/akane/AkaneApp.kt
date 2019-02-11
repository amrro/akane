package app.akane

import android.app.Activity
import android.app.Application
import android.util.Log
import app.akane.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.dean.jraw.android.SharedPreferencesTokenStore
import net.dean.jraw.android.SimpleAndroidLogAdapter
import net.dean.jraw.http.SimpleHttpLogger
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject


class AkaneApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    @Inject
    lateinit var MainaccountHelper: AccountHelper

    @Inject
    lateinit var tokenStorePrefs: SharedPreferencesTokenStore

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        // Every time we use the AccountHelper to switch between accounts (from one account to
        // another, or into/out of userless mode), call this function
        MainaccountHelper.onSwitch { redditClient ->
            // By default, JRAW logs HTTP activity to System.out. We're going to use Log.i()
            // instead.
            val logAdapter = SimpleAndroidLogAdapter(Log.INFO)

            // We're going to use the LogAdapter to write down the summaries produced by
            // SimpleHttpLogger
            redditClient.logger = SimpleHttpLogger(SimpleHttpLogger.DEFAULT_LINE_LENGTH, logAdapter)

            // If you want to disable logging, use a NoopHttpLogger instead:
            // redditClient.setLogger(new NoopHttpLogger());
        }
    }

    override fun activityInjector() = dispatchingAndroidInjector

    fun getAccountHelper(): AccountHelper {
        return MainaccountHelper
    }

    fun getTokenStore(): SharedPreferencesTokenStore {
        return tokenStorePrefs
    }
}