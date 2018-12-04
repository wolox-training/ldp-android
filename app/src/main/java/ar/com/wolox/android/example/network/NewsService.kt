package ar.com.wolox.android.example.network

import ar.com.wolox.android.example.model.New
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsService {

    @GET("/news")
    fun getNews(
        @Query("_order") order: String = "desc",
        @Query("_page") page: Int = 1,
        @Query("_sort") sort: String = "createdAt"
    ): Call<List<New>>

    @GET
    fun getNewsByUrl(
        @Url url: String
    ): Call<List<New>>
}