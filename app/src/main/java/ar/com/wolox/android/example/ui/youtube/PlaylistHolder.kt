package ar.com.wolox.android.example.ui.youtube

import android.view.View
import ar.com.wolox.android.R
import com.google.api.services.youtube.model.SearchResult
import kotlinx.android.synthetic.main.item_search_result.view.*

class PlaylistHolder(view: View, onVideoClickListener: IVideoClickListener) : VideoHolder(view, onVideoClickListener) {

    override fun bindHolder(video: SearchResult) {
        mVideoItem = video

        itemView.run {
            mVideoTitleText.text = video.snippet.title
            mVideoDescriptionText.text = context.getString(R.string.youtube_playlist)
            mVideoImage.setImageURI(video.snippet.thumbnails.default.url)
        }
    }
}