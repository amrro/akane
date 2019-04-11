package app.akane

import android.app.Activity
import android.app.Application
import app.akane.di.AppInjector
import app.akane.ui.auth.RedditManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.dean.jraw.oauth.AccountHelper
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


class AkaneApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var redditManager: RedditManager

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

        redditManager.onSwitch()
    }

    override fun activityInjector() = dispatchingAndroidInjector

    fun getAccountHelper(): AccountHelper {
        return redditManager.accountHelper
    }
}