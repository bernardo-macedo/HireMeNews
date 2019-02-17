package com.bmacedo.hiremenews.articles

import android.content.res.Resources
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.models.mappers.NewsSourceMapper
import com.squareup.moshi.Moshi

class NewsArticlesRepository(
    private val api: NewsApi,
    private val resource: Resources,
    private val moshi: Moshi,
    private val newsSourceMapper: NewsSourceMapper
)