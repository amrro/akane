package app.akane.ui.feed

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.akane.AkaneApp
import app.akane.data.entity.PostInfo
import app.akane.data.repo.post.PostActionsRepository
import app.akane.util.AppCoroutineDispatchers
import app.akane.util.SnackbarMessage
import app.akane.util.browse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject


class ActionsViewModel @Inject constructor(
    app: Application,
    private val repository: PostActionsRepository,
    private val dispatchers: AppCoroutineDispatchers,
    val snackbarMessage: SnackbarMessage
) : AndroidViewModel(app) {


    private val supervisor = SupervisorJob()
    private val exceptionHandler =
        CoroutineExceptionHandler { _, cause ->
            onError(cause)
        }

    private val scope = viewModelScope + supervisor + exceptionHandler


    fun upvote(postId: String) = safeRequest { repository.upvote(postId) }

    fun downvote(postId: String) = safeRequest { repository.downvote(postId) }

    fun save(postInfo: PostInfo) = safeRequest {
        repository.save(postInfo.id)
        sendMessage("💾 Saved!")
    }

    fun hide(postInfo: PostInfo) = safeRequest { repository.hide(postInfo.id) }

    fun openInBrowser(info: PostInfo) {
        getApplication<AkaneApp>().browse(info.url)
    }

    fun copyPostLink(info: PostInfo) {
        (getApplication<AkaneApp>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?)?.run {
            primaryClip = ClipData.newPlainText(info.title, info.url)
            sendMessage("🧷 Copied! ")
        }
    }

    fun blockUser(info: PostInfo) {
    }

    private fun onError(cause: Throwable) = runOnMain {
        cause.message?.let { sendMessage(it) }
        Timber.e(cause)
    }


    private fun runOnMain(action: () -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            action()
        }
    }

    private fun safeRequest(block: suspend () -> Unit) {

        // TODO: return Completable Signal or CompletableSomething.
        try {
            scope.launch(dispatchers.io) { block() }
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    fun sendMessage(text: String = "") = runOnMain {
        if (text.isNotEmpty()) {
            snackbarMessage.value = text
        }
    }

}