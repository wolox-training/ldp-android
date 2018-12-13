package ar.com.wolox.android.example.ui.youtube

import android.support.annotation.NonNull
import com.google.api.services.youtube.model.SearchResult

interface IVideoClickListener {

    fun onVideoClicked(@NonNull video: SearchResult)
}