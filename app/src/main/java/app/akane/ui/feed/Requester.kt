package app.akane.ui.feed

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.coroutineContext

class Requester(private val scope: CoroutineScope) {
    private val channel = Channel<suspend () -> Unit>()

    init {
        scope.launch { start() }
    }

    fun enqueue(block: suspend () -> Unit) {
        scope.launch { channel.send(block) }
    }

    private suspend fun start() {
        for (request in channel) {
            Timber.d("Requester's scope:  $coroutineContext")
            request()
            delay(1000)
        }
    }

    fun stop() {
        channel.close()
    }
}