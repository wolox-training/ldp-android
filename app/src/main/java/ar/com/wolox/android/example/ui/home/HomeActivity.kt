package ar.com.wolox.android.example.ui.home

import ar.com.wolox.android.R
import ar.com.wolox.android.example.ui.home.news.NewsFragment
import ar.com.wolox.android.example.ui.home.profile.ProfileFragment
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

    private lateinit var mSimpleFragmentPagerAdapter: SimpleFragmentPagerAdapter

    override fun layout() = R.layout.activity_home

    override fun init() {
        mSimpleFragmentPagerAdapter = SimpleFragmentPagerAdapter(supportFragmentManager)

        mSimpleFragmentPagerAdapter.addFragment(mNewsFragment, getString(R.string.home_news))
        mSimpleFragmentPagerAdapter.addFragment(mProfileFragment, getString(R.string.home_profile))

        mViewPager.adapter = mSimpleFragmentPagerAdapter

        mTabLayout.setupWithViewPager(mViewPager)
        setupTabsIcons()
    }

    private fun setupTabsIcons() {
        mTabLayout.apply {
            getTabAt(0)?.setIcon(R.drawable.ic_home_news)
            getTabAt(1)?.setIcon(R.drawable.ic_home_profile)
        }
    }
}
