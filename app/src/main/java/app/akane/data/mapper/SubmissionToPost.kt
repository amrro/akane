package app.akane.data.mapper

import app.akane.data.entity.Post
import app.akane.util.toLocalDateTime
import net.dean.jraw.models.CommentSort
import net.dean.jraw.models.Submission
import javax.inject.Inject

class SubmissionToPost @Inject constructor() : Mapper<Submission, Post> {

    lateinit var listing: String

    override fun invoke(from: List<Submission>): List<Post> {
        return from.map { submission ->
            Post(
                id = submission.id,
                isArchived = submission.isArchived,
                author = submission.author,
                authorFlairText = submission.authorFlairText,
                body = submission.body,
                commentCount = submission.commentCount,
                isContestMode = submission.isContestMode,
                created = submission.created.toLocalDateTime(),
                distinguished = submission.distinguished,
                domain = submission.domain,
                edited = submission.edited?.toLocalDateTime(),
                embeddedMedia = submission.embeddedMedia,
                fullName = submission.fullName,
                isGildable = submission.isGildable,
                gilded = submission.gilded,
                isHidden = submission.isHidden,
                linkFlairText = submission.linkFlairText,
                locked = submission.isLocked,
                isNsfw = submission.isNsfw,
                permalink = submission.permalink,
                postHint = submission.postHint,
                preview = submission.preview,
                isQuarantine = submission.isQuarantine,
                isRemoved = submission.isRemoved,
                reports = submission.reports ?: 0,
                isSaved = submission.isSaved,
                score = submission.score,
                isScoreHidden = submission.isScoreHidden,
                selfText = submission.selfText ?: "",
                isSelfPost = submission.isSelfPost,
                isSpam = submission.isSpam,
                isSpoiler = submission.isSpoiler,
                stickied = submission.isStickied,
                subreddit = submission.subreddit,
                subredditFullName = submission.subredditFullName,
                suggestedSort = submission.suggestedSort ?: CommentSort.TOP,
                thumbnail = submission.thumbnail,
                title = submission.title,
                url = submission.url,
                isVisited = submission.isVisited,
                listing = listing
            )

        }
    }
}