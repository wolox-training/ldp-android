package ar.com.wolox.android.example.ui.detail

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import javax.inject.Inject

class NewsDetailActivity : WolmoActivity() {

    @Inject lateinit var mNewsDetailFragment: NewsDetailFragment

    override fun init() {
        mNewsDetailFragment.arguments = requireArguments()
        replaceFragment(R.id.activity_news_detail_fragment_container, mNewsDetailFragment)
    }

    override fun layout() = R.layout.activity_news_detail

    override fun handleArguments(args: Bundle?) = args != null

    private fun requireArguments() = intent.extras!!
}
