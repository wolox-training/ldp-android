package ar.com.wolox.android.example.ui.detail

import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.model.NewChangedEvent
import ar.com.wolox.android.example.network.NewsService
import ar.com.wolox.android.example.utils.Constants
import ar.com.wolox.android.example.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import org.greenrobot.eventbus.EventBus
import java.net.UnknownHostException
import javax.inject.Inject

class NewsDetailPresenter @Inject constructor(
    mRetrofitServices: RetrofitServices,
    private val mSharedPreferencesManager: SharedPreferencesManager
) : BasePresenter<INewsDetailView>() {

    private val mNewsService: NewsService = mRetrofitServices.getService(NewsService::class.java)

    internal lateinit var new: New

    fun getNewById() {
        mNewsService
                .getNewsById(new.id)
                .enqueue(networkCallback {
                    onResponseSuccessful {
                        runIfViewAttached { iNewsDetailView ->
                            iNewsDetailView.hideRefreshing()

                            when (it) {
                                null -> iNewsDetailView.onNewNoFound()
                                else -> {
                                    new = it
                                    iNewsDetailView.onNewLoaded(it)
                                }
                            }
                        }
                    }

                    onResponseFailed { _, _ ->
                        runIfViewAttached { iNewsDetailView ->
                            iNewsDetailView.run {
                                hideRefreshing()
                                onUnexpectedError()
                            }
                        }
                    }

                    onCallFailure {
                        runIfViewAttached { iNewsDetailView ->
                            iNewsDetailView.run {
                                hideRefreshing()

                                when (it) {
                                    is UnknownHostException -> onNoConnection()
                                    else -> onUnexpectedError()
                                }
                            }
                        }
                    }
                })
    }

    fun likeNew() {
        view.onLikeDisable()
        val likes = new.likes
        val userId = getUserId()

        val newLikeList = if (likes.contains(userId)) likes.minus(userId) else likes.plus(userId)
        val currentNew = new.copy(likes = newLikeList)

        mNewsService
                .likeNew(new.id, currentNew)
                .enqueue(networkCallback {
                    onResponseSuccessful { newUpdated ->
                        runIfViewAttached { iNewsDetailView ->
                            newUpdated?.let {
                                iNewsDetailView.onLikeEnable()
                                iNewsDetailView.onNewLoaded(it)
                                new = newUpdated
                                EventBus.getDefault().post(NewChangedEvent(it))
                            }
                        }
                    }

                    onResponseFailed { _, _ ->
                        runIfViewAttached { iNewsDetailView ->
                            iNewsDetailView.onLikeEnable()
                            iNewsDetailView.onUnexpectedError()
                        }
                    }

                    onCallFailure {
                        runIfViewAttached { iNewsDetailView ->
                            iNewsDetailView.onLikeEnable()
                            when (it) {
                                is UnknownHostException -> iNewsDetailView.onNoConnection()
                                else -> iNewsDetailView.onUnexpectedError()
                            }
                        }
                    }
                })
    }

    fun getUserId(): Int = mSharedPreferencesManager.get(Constants.UserCredentials.USER_ID, EMPTY_USER_ID)

    companion object {
        private const val EMPTY_USER_ID = -1
    }
}
