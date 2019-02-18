package com.bmacedo.hiremenews.models.api.article

import com.bmacedo.hiremenews.models.api.GenericResponse
import com.squareup.moshi.Json

data class NewsArticleItemResponse(
    @Json(name = "author") val author: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "urlToImage") val urlToImage: String?,
    @Json(name = "publishedAt") val publishedAt: String?,
    @Json(name = "content") val content: String?
) : GenericResponse()