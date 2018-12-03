package ar.com.wolox.android.example.ui.home.news

import android.support.v7.widget.RecyclerView
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import kotlinx.android.synthetic.main.item_new.view.*

class NewsHolder(view: View, private val mOnNewClickListener: INewClickListener) : RecyclerView.ViewHolder(view) {

    private lateinit var mNew: New
    var mUserLoggedIn: Int = -1

    init {
        itemView.setOnClickListener {
            mOnNewClickListener.onNewClicked(mNew)
        }
    }

    fun bindHolder(item: New) {
        mNew = item

        itemView.mNewTitleText.text = item.title
        itemView.mNewText.text = item.text

        itemView.mTimeAgoText.text = ar.com.wolox.android.example.utils.DateUtils.toDuration(item.createdAt)

        itemView.mLikeIcon.setImageResource(if (item.likes.contains(mUserLoggedIn)) R.drawable.ic_like_on else R.drawable.ic_like_off)
        itemView.mNewImage.setImageURI(item.picture)
    }
}
