package com.bmacedo.hiremenews.models.mappers

import com.bmacedo.hiremenews.models.api.article.NewsArticleListResponse
import com.bmacedo.hiremenews.models.domain.NewsArticle
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsArticleMapper : DomainMapper<NewsArticleListResponse, List<NewsArticle>> {

    override fun toDomainModel(response: NewsArticleListResponse?): List<NewsArticle>? {
        if (response == null) return emptyList()

        val newsArticles = mutableListOf<NewsArticle>()

        response.sources.forEach { articleItem ->
            newsArticles.add(
                NewsArticle(
                    author = articleItem.author,
                    title = articleItem.title,
                    description = articleItem.description,
                    url = articleItem.url,
                    urlToImage = articleItem.urlToImage,
                    publishedAt = parseDate(articleItem.publishedAt),
                    content = articleItem.content
                )
            )
        }

        return newsArticles
    }

    private fun parseDate(publishedAt: String?): Date? {
        val dateFormat = SimpleDateFormat.getDateTimeInstance()
        return try {
            dateFormat.parse(publishedAt)
        } catch (e: ParseException) {
            null
        }

    }
}