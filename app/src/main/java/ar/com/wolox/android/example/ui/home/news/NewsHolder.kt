package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindHolder(item: New) {
        itemView.mNewTitle.text = item.title
        itemView.mNewText.text = item.text
        itemView.mLikesView.text = item.likes.size.toString()

        itemView.mLikeIcon.setImageResource(R.drawable.ic_like_off)
        itemView.mNewImage.setImageResource(R.drawable.img_login_cover)
    }
}
