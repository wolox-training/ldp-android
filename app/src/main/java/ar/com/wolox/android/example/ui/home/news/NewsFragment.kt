package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

/**
 * Fragment for news feature;
 */
class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    @Inject
    lateinit var mNewsAdapter: NewsAdapter

    @Inject
    lateinit var mToastFactory: ToastFactory

    override fun init() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mNewsList.layoutManager = layoutManager
        mNewsList.adapter = mNewsAdapter
    }

    override fun setUi(v: View?) {
        super.setUi(v)
        mRefreshNewsSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
    }

    override fun layout(): Int = R.layout.fragment_news

    override fun setListeners() {
        super.setListeners()

        mRefreshNewsSwipe.setOnRefreshListener {
            showRefreshing()
            presenter.requestNews()
        }

        mNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // TODO("Ask for more news")
                }
            }
        })
    }

    override fun populate() {
        super.populate()
        presenter.requestNews()
    }

    override fun showRefreshing() {
        mRefreshNewsSwipe?.isRefreshing = true
    }

    override fun hideRefreshing() {
        mRefreshNewsSwipe?.isRefreshing = false
    }

    override fun onNewsNotFound() {
        mToastFactory.showLong(R.string.home_news_not_found)
    }

    override fun appendNews(newsList: List<New>) {
        mNewsAdapter.appendNews(newsList)
    }
}
