package com.bmacedo.hiremenews.models.api

import com.squareup.moshi.Json

open class GenericResponse(
    @Json(name = "status") var status: String = "error",
    @Json(name = "code") var statusCode: String? = null,
    @Json(name = "message") var message: String? = null
) {

    fun hasError(): Boolean {
        return status != "ok"
    }
}