package ar.com.wolox.android.example.model

import java.io.Serializable

/**
 * Model to wrap a [com.google.api.services.youtube.model.SearchResult] to pass ids as extras
 */
data class SupportedYouTubeObject(val kind: String, val playlistId: String?, val videoId: String?) : Serializable