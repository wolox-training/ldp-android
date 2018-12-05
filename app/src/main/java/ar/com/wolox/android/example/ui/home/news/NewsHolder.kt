package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*
import org.joda.time.format.DateTimeFormat
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindHolder(item: New) {
        itemView.mNewTitle.text = item.title
        itemView.mNewText.text = item.text

        val localDate = DateTimeFormat
                .forPattern(itemView.context.getString(R.string.app_date_format))
                .withLocale(Locale.getDefault())
                .parseLocalDate(item.createdAt)
                .toDate()

        itemView.mTimeAgo.text = PrettyTime().format(localDate)

        itemView.mLikeIcon.setImageResource(R.drawable.ic_like_off)
        itemView.mNewImage.setImageURI(item.picture)
    }
}
