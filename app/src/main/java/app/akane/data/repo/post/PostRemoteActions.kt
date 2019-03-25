package app.akane.data.repo.post

import app.akane.util.buildRequest
import net.dean.jraw.models.VoteDirection
import net.dean.jraw.oauth.AccountHelper
import javax.inject.Inject

class PostRemoteActions @Inject constructor(
    accountHelper: AccountHelper
) {
    private val reddit = accountHelper.reddit


    suspend fun vote(id: String, direction: VoteDirection) = buildRequest {
        reddit.submission(id).setVote(direction)
    }

    suspend fun save(id: String, saved: Boolean = true) = buildRequest {
        reddit.submission(id).setSaved(saved)
    }

    suspend fun hide(id: String, hidden: Boolean = true) = buildRequest {
        reddit.submission(id).setHidden(hidden)
    }
}