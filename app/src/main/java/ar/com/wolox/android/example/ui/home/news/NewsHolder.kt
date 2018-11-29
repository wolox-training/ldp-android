package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*
import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindHolder(item: New) {
        itemView.mNewTitle.text = item.title
        itemView.mNewText.text = item.text

        val localDate: java.util.Date = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withLocale(java.util.Locale.getDefault())
                .parseLocalDate(item.createdAt)
                .toDate()

        val timeAgo = PrettyTime().format(localDate)
        itemView.mTimeAgo.text = timeAgo

        itemView.mLikeIcon.setImageResource(R.drawable.ic_like_off)
        itemView.mNewImage.setImageURI(item.picture)
    }
}
