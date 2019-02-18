package com.bmacedo.hiremenews.articles

import android.content.res.Resources
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.models.mappers.NewsArticleMapper
import com.bmacedo.hiremenews.utils.onErrorMapMessage
import com.squareup.moshi.Moshi
import io.reactivex.Single

class NewsArticlesRepository(
    private val api: NewsApi,
    private val resource: Resources,
    private val moshi: Moshi,
    private val newsArticleMapper: NewsArticleMapper
) {

    /**
     * Retrieves all the articles related to a given news source.
     * Eg. All articles from BBC.
     */
    fun getArticlesFromSource(sourceId: String, page: Int): Single<List<NewsArticle>> {
        return api.getArticles(sourceId, page, resource.getString(R.string.NEWS_API_KEY))
            .onErrorMapMessage(moshi)
            .map { newsArticleMapper.toDomainModel(it) }
    }

}