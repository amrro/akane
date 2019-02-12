package app.akane.ui.feed.popular

import net.dean.jraw.models.Submission

data class FeedViewState(
        val isLoading: Boolean = false,
        val feed: List<Submission> = emptyList()
)