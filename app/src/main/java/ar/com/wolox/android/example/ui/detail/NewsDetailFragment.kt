package ar.com.wolox.android.example.ui.detail

import android.os.Bundle
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.utils.DateUtils
import ar.com.wolox.android.example.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_news_detail.*
import javax.inject.Inject

class NewsDetailFragment @Inject constructor() : WolmoFragment<NewsDetailPresenter>(), INewsDetailView {

    @Inject lateinit var mToastFactory: ToastFactory

    override fun handleArguments(arguments: Bundle?): Boolean {
        arguments?.let {
            val new = it.getSerializable(NewsDetailFragment.NEWS_OBJECT) as New
            presenter.new = new
            return true
        }

        return false
    }

    override fun init() {
        onNewLoaded(presenter.new)
    }

    override fun layout() = R.layout.fragment_news_detail

    override fun setListeners() {
        super.setListeners()

        mRefreshNewSwipe.setOnRefreshListener {
            presenter.getNewById()
        }

        mLikeButton.onClickListener {
            presenter.likeNew()
        }
    }

    override fun setUi(v: View?) {
        super.setUi(v)
        mRefreshNewSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)

        mBackImage.setOnClickListener { onBackPressed() }
    }

    override fun hideRefreshing() {
        mRefreshNewSwipe.isRefreshing = false
    }

    override fun onBackPressed(): Boolean {
        activity?.finish()
        return true
    }

    override fun onLikeDisable() {
        mLikeButton.isEnabled = false
    }

    override fun onLikeEnable() {
        mLikeButton.isEnabled = true
    }

    override fun onNewLoaded(new: New) {
        new.run {
            mNewTitleText.text = title
            mNewText.text = text
            mTimeAgoText.text = DateUtils.toDuration(createdAt)
            mNewImage.setImageURI(picture)
            mLikeButton.setImageResource(if (likes.contains(presenter.getUserId())) R.drawable.ic_like_on else R.drawable.ic_like_off)
        }
    }

    override fun onNewNoFound() {
        mToastFactory.showLong(R.string.news_detail_new_no_found)
    }

    override fun onNoConnection() {
        mToastFactory.showLong(R.string.app_user_not_connected)
    }

    override fun onUnexpectedError() {
        mToastFactory.showLong(R.string.app_unexpected_error)
    }

    companion object {
        const val NEWS_OBJECT = "news_object"
    }
}
