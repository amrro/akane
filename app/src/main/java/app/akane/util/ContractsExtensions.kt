package app.akane.util

import app.akane.BuildConfig
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


inline fun String?.notNullOrEmpty(lazyMessage: () -> Any): String {
    require(!this.isNullOrEmpty(), lazyMessage)
    return this
}


/**
 * This checker works only in debug mode in order to catch any
 * bug as early as possible.
 */
fun checker(predicate: Boolean, lazyMessage: () -> Any = {}) {
    if (BuildConfig.DEBUG) {
        require(predicate, lazyMessage)
    }
}


suspend fun <T> buildRequest(block: () -> T) = suspendCoroutine<T> { con ->
    try {
        con.resume(block())
    } catch (ex: Exception) {
        con.resumeWithException(ex)
    }
}
