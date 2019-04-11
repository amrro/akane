package app.akane.data.repo.post

import app.akane.data.repo.feed.FeedLocalDataSource
import app.akane.util.checker
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

        // NOTE: Supposedly, the localPost shouldn't be null at all.
        checker(localPost != null) {
            "PostActionsRepository.vote(postId: $postId, dir: $dir): How on earth localPost is null!"
        }

        if (localPost == null) {
            remoteActions.vote(postId, dir)
            return
        }

        // if the vote direction matches the local copy. set to NONE.
        val finalDestination =
            if (localPost.vote == dir) VoteDirection.NONE else dir

        Timber.d("final Destination::: $finalDestination ")

        remoteActions.vote(postId, finalDestination)
            .also { localActions.updatePost(localPost.copy(vote = finalDestination)) }
    }

    suspend fun save(postId: String) {
        val localPost = localActions.getPostWithId(postId)

        // NOTE: Supposedly, the localPost shouldn't be null.
        checker(localPost != null) {
            "PostActionsRepository.save(postId: $postId): How on earth localPost is null!"
        }

        if (localPost == null) {
            remoteActions.save(postId)
            return
        }

        remoteActions.save(postId, !localPost.isSaved)
            .also { localActions.updatePost(localPost.copy(isSaved = !localPost.isSaved)) }
    }

    suspend fun hide(postId: String) {
        val localPost = localActions.getPostWithId(postId)

        // NOTE: Supposedly, the localPost shouldn't be null.
        checker(localPost != null) {
            "PostActionsRepository.save(postId: $postId): How on earth localPost is null!"
        }

        if (localPost == null) {
            remoteActions.hide(postId)
            return
        }

        remoteActions.hide(postId, !localPost.isHidden)
            .also { localActions.updatePost(localPost.copy(isHidden = !localPost.isHidden)) }
    }
}