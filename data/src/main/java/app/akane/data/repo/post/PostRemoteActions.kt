package app.akane.data.repo.post

import app.akane.ui.auth.RedditManager
import net.dean.jraw.models.VoteDirection
import javax.inject.Inject

class PostRemoteActions @Inject constructor(
    private val redditManager: RedditManager
) {

    suspend fun vote(id: String, direction: VoteDirection) = redditManager.request {
        submission(id).setVote(direction)
    }

    suspend fun save(id: String, saved: Boolean = true) = redditManager.request {
        submission(id).setSaved(saved)
    }

    suspend fun hide(id: String, hidden: Boolean = true) = redditManager.request {
        submission(id).setHidden(hidden)
    }
}