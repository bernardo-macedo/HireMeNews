package com.bmacedo.hiremenews.models.api

import com.squareup.moshi.Json

data class NewsSourceListResponse(
    @Json(name = "sources") val sources: List<NewsSourceItemResponse> = emptyList()
) : GenericResponse()