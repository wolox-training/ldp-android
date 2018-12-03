package ar.com.wolox.android.example.ui.detail

import ar.com.wolox.android.example.model.New

interface INewsDetailView {

    fun hideRefreshing()
    fun onLikeDisable()
    fun onLikeEnable()
    fun onNewLoaded(new: New)
    fun onNewNoFound()
    fun onNoConnection()
    fun onUnexpectedError()
}
