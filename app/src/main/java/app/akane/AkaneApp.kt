package app.akane

import android.app.Activity
import android.app.Application
import android.util.Log
import app.akane.di.AppInjector
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.dean.jraw.android.SimpleAndroidLogAdapter
import net.dean.jraw.http.SimpleHttpLogger
import net.dean.jraw.oauth.AccountHelper
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class AkaneApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mainAccountHelper: AccountHelper

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Fresco.initialize(this)
        AppInjector.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)


        // Every time we use the AccountHelper to switch between accounts (from one account to
        // another, or into/out of userless mode), call this function
        mainAccountHelper.onSwitch { redditClient ->
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
        return mainAccountHelper
    }
}