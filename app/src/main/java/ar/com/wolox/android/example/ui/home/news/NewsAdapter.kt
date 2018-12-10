package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import javax.inject.Inject

class NewsAdapter @Inject constructor(private val mNewClockListener: INewClickListener, private val mUserLoggedIn: Int) : RecyclerView.Adapter<NewsHolder>() {

    private var mNewsList: MutableList<New> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.item_new, viewGroup, false)
        return NewsHolder(itemView, mNewClockListener, mUserLoggedIn)
    }

    override fun getItemCount() = mNewsList.size

    override fun onBindViewHolder(holder: NewsHolder, index: Int) = holder.bindHolder(mNewsList[index])

    fun appendNews(news: List<New>) {
        mNewsList.addAll(news)
        notifyDataSetChanged()
    }

    fun setNews(news: List<New>) {
        mNewsList = ArrayList()
        mNewsList.addAll(news)
        notifyDataSetChanged()
    }

    fun updateNew(new: New) {
        val indexOfNew = mNewsList.indexOf(new)
        mNewsList[indexOfNew] = new
        notifyItemChanged(indexOfNew)
    }
}