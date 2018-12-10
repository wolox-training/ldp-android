package ar.com.wolox.android.example.ui.home.news

import android.support.annotation.NonNull
import ar.com.wolox.android.example.model.New

interface INewsView {

    fun showRefreshing()
    fun hideRefreshing()
    fun onNewsNotFound()
    fun appendNews(@NonNull newsList: List<New>)
    fun setNews(@NonNull newsList: List<New>)
    fun onNoConnection()
    fun onUnexpectedError()
}
