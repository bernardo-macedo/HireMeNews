package com.bmacedo.hiremenews.models.api

import com.squareup.moshi.Json

data class NewsSourceItemResponse(
    @Json(name = "id") val id: String = "",
    @Json(name = "name") val name: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "category") val category: String? = null,
    @Json(name = "language") val language: String? = null,
    @Json(name = "country") val country: String? = null
) : GenericResponse()