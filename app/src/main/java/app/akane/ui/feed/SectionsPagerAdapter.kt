package app.akane.ui.feed

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.akane.R
import app.akane.ui.feed.home.HomeFeedFragment
import app.akane.ui.feed.popular.PopularFeedFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_home,
        R.string.tab_popular
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PopularFeedFragment (defined as a static inner class below).
//        return PopularFeedFragment.newInstance(position + 1)

        return when (position) {
            0 -> HomeFeedFragment()
            1 -> PopularFeedFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}