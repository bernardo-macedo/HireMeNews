package com.bmacedo.hiremenews.app.api

import com.bmacedo.hiremenews.models.api.article.NewsArticleListResponse
import com.bmacedo.hiremenews.models.api.source.NewsSourceListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rest API for communication with the NewsApi backend service
 */
interface NewsApi {

    @GET("v2/sources")
    fun getNewsSources(@Query("apiKey") apiKey: String): Single<NewsSourceListResponse>

    @GET("v2/everything")
    fun getArticles(
        @Query("sources") sourceId: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsArticleListResponse>

}