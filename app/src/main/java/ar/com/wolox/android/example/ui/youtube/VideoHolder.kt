package ar.com.wolox.android.example.ui.youtube

import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.example.utils.onClickListener
import com.google.api.services.youtube.model.SearchResult
import kotlinx.android.synthetic.main.item_search_result.view.*

open class VideoHolder(view: View, onVideoClickListener: IVideoClickListener) : RecyclerView.ViewHolder(view) {

    lateinit var mVideoItem: SearchResult

    init {
        itemView.onClickListener {
            onVideoClickListener.onVideoClicked(mVideoItem)
        }
    }

    open fun bindHolder(video: SearchResult) {
        mVideoItem = video

        itemView.run {
            mVideoImage.setImageURI(video.snippet.thumbnails.default.url)
            mVideoTitleText.text = video.snippet.title
            mVideoDescriptionText.text = video.snippet.description
        }
    }
}