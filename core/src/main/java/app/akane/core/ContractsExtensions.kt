package app.akane.core

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> buildRequest(block: () -> T) = suspendCoroutine<T> { con ->
    try {
        con.resume(block())
    } catch (ex: Exception) {
        con.resumeWithException(ex)
    }
}
