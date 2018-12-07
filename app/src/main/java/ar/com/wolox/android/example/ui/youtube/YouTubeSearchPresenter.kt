package ar.com.wolox.android.example.ui.youtube

import android.support.annotation.NonNull
import android.util.Log
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import java.net.UnknownHostException
import javax.inject.Inject

class YouTubeSearchPresenter @Inject constructor() : BasePresenter<IYouTubeSearchView>() {

    private val mYouTube = YouTube
            .Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), null)
            .build()

    private var mNextPageToken: String? = null
    private var mIsLoading = false

    fun onYouTubeSearch(@NonNull query: String, @NonNull apiKey: String, paginated: Boolean = false) {
        val search = mYouTube.search().list("snippet")

        search.apply {
            key = apiKey
            q = query
            maxResults = MAX_VIDEOS

            if (mNextPageToken != null && paginated) {
                pageToken = mNextPageToken
            }
        }

        mIsLoading = true

        Thread {
            try {
                val result = search.execute()
                Log.d("ldpenal", "here'")

                runIfViewAttached { iYouTubeSearchView ->
                    if (result == null) {
                        iYouTubeSearchView.setResults(mutableListOf())
                        return@runIfViewAttached
                    } else {
                        mNextPageToken = result.nextPageToken

                        if (paginated) {
                            iYouTubeSearchView.appendResults(result.items)
                        } else {
                            iYouTubeSearchView.setResults(result.items)
                        }
                    }
                }
            } catch (error: Exception) {
                runIfViewAttached { iYouTubeSearchView ->
                    when (error) {
                        is UnknownHostException -> iYouTubeSearchView.onNoConnection()
                        else -> iYouTubeSearchView.onUnexpectedError()
                    }
                }
            } finally {
                mIsLoading = false
            }
        }.start()
    }

    fun hasMore() = mNextPageToken != null

    fun isLoading() = mIsLoading

    companion object {
        private const val MAX_VIDEOS = 10L
        private const val TAG = "mYouTube"
    }
}
