package app.akane.ui.feed

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.akane.R

private val TAB_TITLES = arrayOf(
        R.string.tab_home,
        R.string.tab_popular
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class HomePagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> FeedListFragment.newInstance("frontpage")
        else -> FeedListFragment.newInstance("popular")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}