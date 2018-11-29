package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.model.New
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

// TODO: implement logic
class NewsPresenter @Inject constructor(private val mRetrofitServices: RetrofitServices) : BasePresenter<INewsView>() {

    fun requestNews() {
        val newList: ArrayList<New> = ArrayList()
        for (i in 1..20) {
            newList.add(New(i, ArrayList(), "", "News title", "News text"))
        }

        runIfViewAttached { iNewsView ->
            iNewsView.hideRefreshing()
            iNewsView.appendNews(newList)
        }
    }
}
