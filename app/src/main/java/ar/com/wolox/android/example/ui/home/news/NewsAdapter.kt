package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsHolder>() {

    private var mNewsList: MutableList<New> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.item_new, viewGroup, false)
        return NewsHolder(itemView)
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
}