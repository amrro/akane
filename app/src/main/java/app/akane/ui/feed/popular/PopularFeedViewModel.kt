package app.akane.ui.feed.popular

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

class PopularFeedViewModel @Inject constructor(
        val accountHelper: AccountHelper
) : ViewModel() {

    val reddit = accountHelper.reddit

    val snackbarMessage = SnackbarMessage()

    fun vote(submission: Submission, dir: VoteDirection) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val subRef = submission.toReference(reddit)
                subRef.setVote(
                        if (subRef.inspect().vote == dir) VoteDirection.NONE
                        else dir
                )
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
            }
        }
    }

    fun hideSubmission(submission: Submission) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val subRef = submission.toReference(reddit)
                subRef.setHidden(!subRef.inspect().isHidden)
            } catch (e: ApiException) {
                snackbarMessage.value = e.explanation
            }
        }

    }

    fun saveSubmission(submission: Submission) {
        try {
            val submissionReference = submission.toReference(reddit)
            submissionReference.setSaved(!submissionReference.inspect().isSaved)
        } catch (e: ApiException) {
            snackbarMessage.value = e.explanation
        }

    }
}