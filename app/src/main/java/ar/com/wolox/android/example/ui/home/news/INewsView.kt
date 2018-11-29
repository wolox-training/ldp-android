package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.model.New

interface INewsView {

    fun showRefreshing()
    fun hideRefreshing()
    fun onNewsNotFound()
    fun appendNews(newsList: List<New>)
}
