package com.bmacedo.hiremenews.utils

import com.bmacedo.hiremenews.models.api.GenericResponse
import com.squareup.moshi.Moshi
import io.reactivex.Single
import retrofit2.HttpException

fun <T> Single<T>.onErrorMapMessage(moshi: Moshi): Single<T> {
    return this.onErrorResumeNext { exception ->
        if (exception is HttpException) {
            val errorBody = exception.response().errorBody()?.string()
            if (errorBody != null) {
                val result = moshi.adapter(GenericResponse::class.java).fromJson(errorBody)
                return@onErrorResumeNext Single.error(RuntimeException(result?.message))
            }
        }
        return@onErrorResumeNext Single.error(exception)
    }
}