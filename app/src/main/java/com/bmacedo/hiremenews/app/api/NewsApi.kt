package com.bmacedo.hiremenews.app.api

import com.bmacedo.hiremenews.models.api.NewsSourceListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rest API for communication with the NewsApi backend service
 */
interface NewsApi {

    @GET("v2/sources")
    fun getNewsSources(@Query("apiKey") apiKey: String): Single<NewsSourceListResponse>

}