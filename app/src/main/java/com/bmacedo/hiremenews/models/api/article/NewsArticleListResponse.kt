package com.bmacedo.hiremenews.models.api.article

import com.bmacedo.hiremenews.models.api.GenericResponse
import com.squareup.moshi.Json

data class NewsArticleListResponse(
    @Json(name = "totalResults") val totalResults: Int = 0,
    @Json(name = "articles") val articles: List<NewsArticleItemResponse> = emptyList()
) : GenericResponse()