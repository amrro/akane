package app.akane.ui.feed.home

import androidx.lifecycle.ViewModel
import app.akane.util.SnackbarMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dean.jraw.ApiException
import net.dean.jraw.models.Submission
import net.dean.jraw.models.VoteDirection
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        val accountHelper: AccountHelper
) : ViewModel() {

    val snackbarMessage = SnackbarMessage()

    fun vote(submission: Submission, dir: VoteDirection) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                submission.toReference(accountHelper.reddit).setVote(dir)
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
            }
        }
    }

    fun hideSubmission() {

    }

    fun saveSubmission() {

    }
}