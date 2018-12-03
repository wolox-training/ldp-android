package ar.com.wolox.android.example.ui.home.news

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.model.NewChangedEvent
import ar.com.wolox.android.example.ui.detail.NewsDetailActivity
import ar.com.wolox.android.example.ui.detail.NewsDetailFragment
import ar.com.wolox.android.example.utils.InfiniteScroll
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_news.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Fragment for news feature;
 */
class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    val TAG = NewsFragment::class.java.simpleName
    private val VIEW_THRESHOLD = 10

    lateinit var mNewsAdapter: NewsAdapter

    @Inject
    lateinit var mToastFactory: ToastFactory

    override fun init() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mNewsList.layoutManager = layoutManager

        mNewsAdapter = NewsAdapter(object : INewClickListener {
            override fun onNewClicked(new: New) {
                openNewDetail(new)
            }
        }, presenter.userLoggedIn())

        mNewsList.adapter = mNewsAdapter

        EventBus.getDefault().register(this)
    }

    override fun setUi(v: View?) {
        super.setUi(v)
        mRefreshNewsSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)

        mCreateNewButton.attachToRecyclerView(mNewsList)
    }

    override fun layout(): Int = R.layout.fragment_news

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
                return (presenter.hasMore()) && (lastVisibleItem >= (layoutManager.itemCount - VIEW_THRESHOLD))
            }

            override fun isLoading(): Boolean = mRefreshNewsSwipe.isRefreshing
        })
    }

    override fun populate() {
        super.populate()
        presenter.requestNews(paginated = false)
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

    override fun appendNews(newsList: List<New>) = mNewsAdapter.appendNews(newsList)

    override fun setNews(newsList: List<New>) = mNewsAdapter.setNews(newsList)

    override fun onNoConnection() = mToastFactory.showLong(R.string.app_user_not_connected)

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onUnexpectedError() = mToastFactory.showLong(R.string.app_unexpected_error)

    fun openNewDetail(new: New) {
        val newsDetailIntent = Intent(context, NewsDetailActivity::class.java)
        newsDetailIntent.putExtra(NewsDetailFragment.NEWS_OBJECT, new)
        startActivity(newsDetailIntent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewChanged(newChangedEvent: NewChangedEvent) = mNewsAdapter.updateNew(newChangedEvent.new)
}
