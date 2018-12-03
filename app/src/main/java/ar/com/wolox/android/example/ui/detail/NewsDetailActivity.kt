package ar.com.wolox.android.example.ui.detail

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import javax.inject.Inject

class NewsDetailActivity : WolmoActivity() {

    @Inject lateinit var mNewsDetailFragment: NewsDetailFragment

    private lateinit var mFragmentExtras: Bundle
    private val TAG = NewsDetailActivity::class.java.simpleName

    override fun init() {
        mNewsDetailFragment.arguments = mFragmentExtras
        replaceFragment(R.id.activity_news_detail_fragment_container, mNewsDetailFragment)
    }

    override fun layout() = R.layout.activity_news_detail

    override fun handleArguments(args: Bundle?): Boolean {
        args?.let {
            mFragmentExtras = args
            return true
        }

        return false
    }
}
