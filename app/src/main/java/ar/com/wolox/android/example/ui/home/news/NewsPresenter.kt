package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.network.NewsService
import ar.com.wolox.android.example.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val mRetrofitServices: RetrofitServices) : BasePresenter<INewsView>() {

    fun requestNews() {
        mRetrofitServices
                .getService(NewsService::class.java)
                .getNews()
                .enqueue(networkCallback {
                    onResponseSuccessful { newsList ->
                        runIfViewAttached { iNewsView ->
                            newsList?.let {
                                if (newsList.isNotEmpty()) {
                                    iNewsView.appendNews(it)
                                } else {
                                    iNewsView.onNewsNotFound()
                                }
                            }

                            iNewsView.hideRefreshing()
                        }
                    }

                    onResponseFailed { _, _ ->
                        runIfViewAttached { iNewsView -> iNewsView.hideRefreshing() }
                    }

                    onCallFailure {
                        runIfViewAttached { iNewsView -> iNewsView.hideRefreshing() }
                    }
                })
    }
}
