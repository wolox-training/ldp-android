package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.utils.InfiniteScroll
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

/**
 * Fragment for news feature;
 */
class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    @Inject lateinit var mNewsAdapter: NewsAdapter
    @Inject lateinit var mToastFactory: ToastFactory

    override fun init() {
        mNewsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mNewsList.adapter = mNewsAdapter
    }

    override fun setUi(v: View?) {
        super.setUi(v)
        mRefreshNewsSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)

        mCreateNewButton.attachToRecyclerView(mNewsList)
    }

    override fun layout() = R.layout.fragment_news

    override fun setListeners() {
        super.setListeners()

        mRefreshNewsSwipe.setOnRefreshListener {
            showRefreshing()
            presenter.requestNews(paginated = false)
        }

        mNewsList.addOnScrollListener(object : InfiniteScroll({
            showRefreshing()
            presenter.requestNews(paginated = true)
        }) {
            override fun hasMore(): Boolean {
                val layoutManager = mNewsList.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                return presenter.hasMore() && lastVisibleItem >= (layoutManager.itemCount - VIEW_THRESHOLD)
            }

            override fun isLoading(): Boolean = mRefreshNewsSwipe.isRefreshing
        })
    }

    override fun populate() {
        super.populate()
        presenter.requestNews(paginated = false)
    }

    override fun showRefreshing() {
        mRefreshNewsSwipe.isRefreshing = true
    }

    override fun hideRefreshing() {
        mRefreshNewsSwipe.isRefreshing = false
    }

    override fun onNewsNotFound() = mToastFactory.showLong(R.string.home_news_not_found)

    override fun appendNews(newsList: List<New>) = mNewsAdapter.appendNews(newsList)

    override fun setNews(newsList: List<New>) = mNewsAdapter.setNews(newsList)

    override fun onNoConnection() = mToastFactory.showLong(R.string.app_user_not_connected)

    override fun onUnexpectedError() = mToastFactory.showLong(R.string.app_unexpected_error)

    companion object {
        private const val VIEW_THRESHOLD = 10
    }
}
