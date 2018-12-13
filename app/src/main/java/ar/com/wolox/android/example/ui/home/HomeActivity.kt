package ar.com.wolox.android.example.ui.home

import ar.com.wolox.android.R
import ar.com.wolox.android.example.ui.home.news.NewsFragment
import ar.com.wolox.android.example.ui.home.profile.ProfileFragment
import ar.com.wolox.android.example.ui.youtube.YouTubeSearchFragment
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import ar.com.wolox.wolmo.core.adapter.viewpager.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * Application home;
 */
class HomeActivity : WolmoActivity() {

    @Inject internal lateinit var mNewsFragment: NewsFragment
    @Inject internal lateinit var mProfileFragment: ProfileFragment
    @Inject internal lateinit var mYouTubeSearchFragment: YouTubeSearchFragment

    private lateinit var mSimpleFragmentPagerAdapter: SimpleFragmentPagerAdapter

    override fun layout() = R.layout.activity_home

    override fun init() {
        mSimpleFragmentPagerAdapter = SimpleFragmentPagerAdapter(supportFragmentManager)

        mSimpleFragmentPagerAdapter.apply {
            addFragment(mNewsFragment, getString(R.string.home_news))
            addFragment(mProfileFragment, getString(R.string.home_profile))
            addFragment(mYouTubeSearchFragment, getString(R.string.home_youtube_search))
        }

        mViewPager.apply {
            adapter = mSimpleFragmentPagerAdapter
            offscreenPageLimit = 2
        }

        mTabLayout.setupWithViewPager(mViewPager)
        setupTabsIcons()
    }

    private fun setupTabsIcons() {
        mTabLayout.apply {
            getTabAt(0)?.setIcon(R.drawable.ic_home_news)
            getTabAt(1)?.setIcon(R.drawable.ic_home_profile)
            getTabAt(2)?.setIcon(R.drawable.ic_home_youtube)
        }
    }
}
