package app.akane.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.akane.data.repo.post.PostActionsRepository
import app.akane.util.AppCoroutineDispatchers
import app.akane.util.SnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

class ActionsViewModel @Inject constructor(
    private val repository: PostActionsRepository,
    private val dispatchers: AppCoroutineDispatchers,
    val snackbarMessage: SnackbarMessage
) : ViewModel() {

    private val supervisor = SupervisorJob()
    private val exceptionHandler =
        CoroutineExceptionHandler { _, cause ->
            onError(cause)
        }

    private val scope = viewModelScope + supervisor + exceptionHandler


    fun upvote(postId: String) = safeRequest { repository.upvote(postId) }

    fun downvote(postId: String) = safeRequest { repository.downvote(postId) }

    fun save(postId: String) = safeRequest { repository.save(postId) }

    fun hide(postId: String) = safeRequest { repository.hide(postId) }

    private fun onError(cause: Throwable) = runOnMain {
        snackbarMessage.value = cause.message
        Timber.e(cause)
    }

    private fun runOnMain(action: () -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            action()
        }
    }

    private fun safeRequest(block: suspend () -> Unit) {
        try {
            scope.launch(dispatchers.io) { block() }
        } catch (ex: Exception) {
            onError(ex)
        }
    }
}