package app.akane.data.repo.post

import app.akane.ui.auth.RedditManager
import app.akane.util.exception.MustLoginException
import net.dean.jraw.models.VoteDirection
import javax.inject.Inject

class PostRemoteActions @Inject constructor(
    private val redditManager: RedditManager
) {

    suspend fun vote(id: String, direction: VoteDirection) = fireAction {
        redditManager.reddit().submission(id).setVote(direction)
    }

    suspend fun save(id: String, saved: Boolean = true) = fireAction {
        redditManager.reddit().submission(id).setSaved(saved)
    }

    suspend fun hide(id: String, hidden: Boolean = true) = fireAction {
        redditManager.reddit().submission(id).setHidden(hidden)
    }

    @Throws(MustLoginException::class)
    suspend fun fireAction(block: suspend () -> Unit) {
        if (!redditManager.isUserless())
            block()
        else
            throw MustLoginException()
    }
}