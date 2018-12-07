package ar.com.wolox.android.example.ui.youtube

import android.support.annotation.NonNull
import com.google.api.services.youtube.model.SearchResult

interface IYouTubeSearchView {

    fun appendResults(@NonNull videos: MutableList<SearchResult>)
    fun onNoConnection()
    fun onUnexpectedError()
    fun setResults(@NonNull videos: MutableList<SearchResult>)
}
