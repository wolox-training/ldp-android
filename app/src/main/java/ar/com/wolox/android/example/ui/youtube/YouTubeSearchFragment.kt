package ar.com.wolox.android.example.ui.youtube

import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.SearchResultModelAdapter
import ar.com.wolox.android.example.utils.Extras
import ar.com.wolox.android.example.utils.InfiniteScroll
import ar.com.wolox.android.example.utils.onClickListener
import ar.com.wolox.android.example.utils.shouldRequestMore
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import com.google.android.youtube.player.YouTubeIntents
import com.google.api.services.youtube.model.SearchResult
import kotlinx.android.synthetic.main.fragment_youtube_search.*
import javax.inject.Inject

class YouTubeSearchFragment @Inject constructor() : WolmoFragment<YouTubeSearchPresenter>(), IYouTubeSearchView {

    @Inject lateinit var mToastFactory: ToastFactory

    private lateinit var mSearchResultAdapter: SearchResultAdapter

    override fun init() {
        mSearchResultAdapter = SearchResultAdapter(object : IVideoClickListener {
            override fun onVideoClicked(video: SearchResult) = openVideo(video)
        })

        mVideoList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mVideoList.adapter = mSearchResultAdapter
        }
    }

    override fun layout() = R.layout.fragment_youtube_search

    override fun appendResults(@NonNull videos: MutableList<SearchResult>) {
        mVideoList.post {
            mSearchResultAdapter.appendResults(videos)
        }
    }

    override fun onUnexpectedError() {
        mVideoList.post { mToastFactory.showLong(R.string.app_unexpected_error) }
    }

    override fun onNoConnection() {
        mVideoList.post { mToastFactory.showLong(R.string.app_user_not_connected) }
    }

    override fun setListeners() {
        super.setListeners()

        mSearchButton.onClickListener {
            if (!mSearchInput.text.isEmpty()) {
                presenter.onYouTubeSearch(mSearchInput.text.toString(), getString(R.string.app_youtube_api_key))
            } else {
                mSearchInput.error = getString(R.string.home_youtube_no_search_criteria)
            }
        }

        mVideoList.addOnScrollListener(object : InfiniteScroll({
            presenter.onYouTubeSearch(mSearchInput.text.toString(), getString(R.string.app_youtube_api_key), true)
        }) {
            override fun isLoading() = presenter.isLoading()
            override fun hasMore() = presenter.hasMore() && mVideoList.layoutManager?.shouldRequestMore(VIEW_THRESHOLD) ?: false
        })
    }

    override fun setResults(@NonNull videos: MutableList<SearchResult>) {
        mVideoList.post {
            if (videos.isEmpty()) {
                mToastFactory.showLong(R.string.youtube_not_found_videos)
                mSearchResultAdapter.setResults(mutableListOf())
            } else {
                mSearchResultAdapter.setResults(videos)
            }
        }
    }

    fun openVideo(@NonNull result: SearchResult) {
        when (result.id.kind) {
            Extras.YouTubeResultKind.KIND_VIDEO, Extras.YouTubeResultKind.KIND_PLAYLIST -> {
                val videoPlayerIntent = Intent(requireContext(), YouTubeVideoPlayerActivity::class.java)
                videoPlayerIntent.putExtra(YouTubeVideoPlayerActivity.ITEM_OBJECT, SearchResultModelAdapter(result.id.kind, result.id.playlistId, result.id.videoId))
                startActivity(videoPlayerIntent)
            }
            else -> {
                val channelIntent = YouTubeIntents.createChannelIntent(requireContext(), result.id.channelId)
                startActivity(channelIntent)
            }
        }
    }

    companion object {
        private const val VIEW_THRESHOLD = 10
    }
}