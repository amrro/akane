package app.akane.data.repo.post

import app.akane.data.repo.feed.FeedLocalDataSource
import net.dean.jraw.models.VoteDirection
import timber.log.Timber
import javax.inject.Inject

class PostActionsRepository @Inject constructor(
    private val localActions: FeedLocalDataSource,
    private val remoteActions: PostRemoteActions
) {

    suspend fun upvote(postId: String) = vote(postId, VoteDirection.UP)

    suspend fun downvote(postId: String) = vote(postId, VoteDirection.DOWN)

    @Throws(Throwable::class)
    private suspend fun vote(postId: String, dir: VoteDirection) {
        val localPost = localActions.getPostWithId(postId)

        requireNotNull(localPost)

        // if the vote direction matches the local copy. set to NONE.
        val finalDestination =
            if (localPost.vote == dir) VoteDirection.NONE else dir

        Timber.d("final Destination::: $finalDestination ")

        remoteActions.vote(postId, finalDestination)
            .also { localActions.updatePost(localPost.copy(vote = finalDestination)) }
    }

    suspend fun save(postId: String) {
        val localPost = localActions.getPostWithId(postId)

        requireNotNull(localPost)

        remoteActions.save(postId, !localPost.isSaved)
            .also { localActions.updatePost(localPost.copy(isSaved = !localPost.isSaved)) }
    }

    suspend fun hide(postId: String) {
        val localPost = localActions.getPostWithId(postId)

        requireNotNull(localPost)

        remoteActions.hide(postId, !localPost.isHidden)
            .also { localActions.updatePost(localPost.copy(isHidden = !localPost.isHidden)) }
    }
}