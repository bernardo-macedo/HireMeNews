package com.bmacedo.hiremenews.sources

import android.content.res.Resources
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.models.mappers.NewsSourceMapper
import com.bmacedo.hiremenews.utils.onErrorMapMessage
import com.squareup.moshi.Moshi
import io.reactivex.Single


class NewsSourcesRepository(
    private val api: NewsApi,
    private val resource: Resources,
    private val moshi: Moshi,
    private val newsSourceMapper: NewsSourceMapper
) {

    fun getNewsSources(): Single<List<NewsSource>> {
        return api.getNewsSources(resource.getString(R.string.NEWS_API_KEY))
            .onErrorMapMessage(moshi)
            .map { newsSourceMapper.toDomainModel(it) }
    }

}
