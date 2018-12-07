package ar.com.wolox.android.example.ui.youtube

import android.view.View
import ar.com.wolox.android.R
import com.facebook.drawee.generic.RoundingParams
import com.google.api.services.youtube.model.SearchResult
import kotlinx.android.synthetic.main.item_search_result.view.*

class ChannelHolder(view: View, onVideoClickListener: IVideoClickListener) : VideoHolder(view, onVideoClickListener) {

    override fun bindHolder(video: SearchResult) {
        mVideoItem = video

        itemView.run {
            mVideoTitleText.text = video.snippet.channelTitle
            mVideoDescriptionText.text = context.getString(R.string.youtube_channel)
            mVideoImage.hierarchy.roundingParams = RoundingParams.asCircle()
            mVideoImage.setImageURI(video.snippet.thumbnails.default.url)
        }
    }
}