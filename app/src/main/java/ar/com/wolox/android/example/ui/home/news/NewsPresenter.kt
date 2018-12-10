package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.network.NewsService
import ar.com.wolox.android.example.utils.Constants
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val mRetrofitServices: RetrofitServices,
    private val mSharedPreferencesManager: SharedPreferencesManager
) : BasePresenter<INewsView>() {

    var nextPage: String? = null

    fun hasMore() = nextPage != null

    fun requestNews(paginated: Boolean) {
        val newsService = mRetrofitServices.getService(NewsService::class.java)

        if (paginated && nextPage == null) {
            runIfViewAttached(INewsView::hideRefreshing)
            return
        }

        val callable = if (!paginated) newsService.getNews() else newsService.getNewsByUrl(nextPage!!)

        callable.enqueue(object : Callback<List<New>> {
            override fun onFailure(call: Call<List<New>>, error: Throwable?) {
                runIfViewAttached { iNewsView ->
                    iNewsView.hideRefreshing()

                    if (error is UnknownHostException) {
                        iNewsView.onNoConnection()
                    } else {
                        iNewsView.onUnexpectedError()
                    }
                }
            }

            override fun onResponse(call: Call<List<New>>, response: Response<List<New>>) {
                if (!response.isSuccessful) {
                    onFailure(call, null)
                    return
                }

                val headers: Headers = response.headers()

                nextPage = null
                val linkHeader = headers.get("Link")

                if (linkHeader != null) {
                    val pagination = linkHeader
                            .split(";")
                            .filter { split -> split.contains("next", true) }

                    if (pagination.isNotEmpty()) {
                        val foundNext = pagination[0]
                        val startIndex = foundNext.indexOf("<")
                        val endIndex = foundNext.indexOf(">")

                        if (startIndex != -1 && endIndex != -1) {
                            nextPage = foundNext.substring(startIndex + 1, endIndex)
                        }
                    }
                }

                val newsList = response.body()

                runIfViewAttached { iNewsView ->
                    if (newsList == null || newsList.isEmpty()) {
                        iNewsView.onNewsNotFound()
                    } else if (!paginated) {
                        iNewsView.setNews(newsList)
                    } else {
                        iNewsView.appendNews(newsList)
                    }

                    iNewsView.hideRefreshing()
                }
            }
        })
    }

    /**
     * Check whether a user is logged in
     */
    fun userLoggedIn(): Int = mSharedPreferencesManager.get(Constants.UserCredentials.USER_ID, EMPTY_USER)

    companion object {
        private const val EMPTY_USER = -1
    }
}
