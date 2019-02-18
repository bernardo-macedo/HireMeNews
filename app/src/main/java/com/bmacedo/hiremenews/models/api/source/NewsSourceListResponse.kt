package com.bmacedo.hiremenews.models.api.source

import com.bmacedo.hiremenews.models.api.GenericResponse
import com.squareup.moshi.Json

data class NewsSourceListResponse(
    @Json(name = "sources") val sources: List<NewsSourceItemResponse> = emptyList()
) : GenericResponse()