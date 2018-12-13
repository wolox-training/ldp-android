package ar.com.wolox.android.example.ui.youtube

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ar.com.wolox.android.R
import ar.com.wolox.android.example.utils.Extras
import com.google.api.services.youtube.model.SearchResult

class SearchResultAdapter(private val onVideoClickListener: IVideoClickListener) : RecyclerView.Adapter<VideoHolder>() {

    private var mResultList = ArrayList<SearchResult>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VideoHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)

        return when (viewType) {
            TYPE_CHANNEL -> ChannelHolder(layoutInflater.inflate(R.layout.item_search_result, viewGroup, false), onVideoClickListener)
            TYPE_PLAYLIST -> PlaylistHolder(layoutInflater.inflate(R.layout.item_search_result, viewGroup, false), onVideoClickListener)
            else -> VideoHolder(layoutInflater.inflate(R.layout.item_search_result, viewGroup, false), onVideoClickListener)
        }
    }

    override fun getItemCount() = mResultList.size

    override fun getItemViewType(position: Int) = when (mResultList[position].id.kind) {
        Extras.YouTubeResultKind.KIND_VIDEO -> TYPE_VIDEO
        Extras.YouTubeResultKind.KIND_PLAYLIST -> TYPE_PLAYLIST
        else -> TYPE_CHANNEL
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) = holder.bindHolder(mResultList[position])

    fun appendResults(videoList: MutableList<SearchResult>) {
        val oldItemCount = itemCount
        mResultList.addAll(videoList)
        notifyItemRangeInserted(oldItemCount, itemCount)
    }

    fun setResults(videoList: MutableList<SearchResult>) {
        mResultList = ArrayList()
        mResultList.addAll(videoList)
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_CHANNEL = 1
        private const val TYPE_VIDEO = 2
        private const val TYPE_PLAYLIST = 3
    }
}