package ar.com.wolox.android.example.network

import ar.com.wolox.android.example.model.New

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("/news")
    fun getNews(): Call<List<New>>
}