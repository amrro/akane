package app.akane.data.mapper

import app.akane.data.entity.Subreddit
import app.akane.data.toLocalDateTime
import javax.inject.Inject

class SubredditMapper @Inject constructor() {
    operator fun invoke(from: net.dean.jraw.models.Subreddit): Subreddit {
        return Subreddit(
            id = from.id,
            accountsActive = from.accountsActive,
            isAccountsActiveFuzzed = from.isAccountsActiveFuzzed,
            bannerImage = from.bannerImage,
            commentScoreHideMinsNullable = from.commentScoreHideMins,
            created = from.created.toLocalDateTime(),
            hideAds = from.hideAds,
            fullName = from.fullName,
            keyColor = from.keyColor,
            name = from.name,
            isNsfw = from.isNsfw,
            publicDescription = from.publicDescription,
            isQuarantined = from.isQuarantined,
            sidebar = from.sidebar,
            isSpoilersEnabled = from.isSpoilersEnabled,
            submissionType = from.submissionType,
            submitLinkLabel = from.submitLinkLabel,
            submitTextLabel = from.submitTextLabel,
            subscribers = from.subscribers,
            suggestedCommentSort = from.suggestedCommentSort,
            title = from.title,
            url = from.url,
            isUserMuted = from.isUserMuted,
            isUserBanned = from.isUserBanned,
            isUserContributor = from.isUserContributor,
            isUserModerator = from.isUserModerator,
            isUserSubscriber = from.isUserSubscriber,
            userFlairText = from.userFlairText,
            userFlairEnabled = from.isFlairEnabledForUser,
            isUserFlairGenerallyEnabled = from.isUserFlairGenerallyEnabled
        )
    }
}