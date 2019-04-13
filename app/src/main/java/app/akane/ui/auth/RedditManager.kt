package app.akane.ui.auth

import android.util.Log
import app.akane.util.exception.MustLoginException
import net.dean.jraw.RedditClient
import net.dean.jraw.android.SimpleAndroidLogAdapter
import net.dean.jraw.http.SimpleHttpLogger
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditManager @Inject constructor(
    internal val accountHelper: AccountHelper
) {

    suspend fun reddit(): RedditClient {
        return if (accountHelper.isAuthenticated()) accountHelper.reddit
        else accountHelper.switchToUserless()
    }

    fun onSwitch(
        configure: (r: RedditClient) -> Unit = {
            val logAdapter = SimpleAndroidLogAdapter(Log.INFO)
            it.logger = SimpleHttpLogger(SimpleHttpLogger.DEFAULT_LINE_LENGTH, logAdapter)
        }
    ) {
        accountHelper.onSwitch(configure)
    }

    @Throws(MustLoginException::class)
    suspend fun <T> request(block: suspend RedditClient.() -> T): T {
        if (!this@RedditManager.isUserless())
            return block(reddit())
        else
            throw MustLoginException()
    }


    fun isLoggedIn(): Boolean {
        return accountHelper.isAuthenticated()
    }

    fun logout() {
        accountHelper.logout()
    }

    fun isUserless(): Boolean {
        return this.accountHelper.reddit.authManager.currentUsername() == USERNAME_USERLESS
    }

    companion object {

        const val USERNAME_USERLESS = "<userless>"
        val SCOPES = arrayOf("read", "identity", "vote", "save", "report")
    }
}