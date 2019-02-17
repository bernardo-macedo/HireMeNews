package com.bmacedo.hiremenews.models.api

import com.google.gson.annotations.SerializedName

open class GenericResponse(
    @SerializedName("status") var status: String = "error",
    @SerializedName("code") var statusCode: String? = null,
    @SerializedName("message") var message: String? = null
) {

    fun hasError(): Boolean {
        return status != "ok"
    }
}