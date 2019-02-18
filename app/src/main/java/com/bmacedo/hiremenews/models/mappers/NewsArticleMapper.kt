package com.bmacedo.hiremenews.models.mappers

import com.bmacedo.hiremenews.models.api.article.NewsArticleListResponse
import com.bmacedo.hiremenews.models.domain.NewsArticle
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsArticleMapper : DomainMapper<NewsArticleListResponse, List<NewsArticle>> {

    override fun toDomainModel(response: NewsArticleListResponse?): List<NewsArticle>? {
        if (response == null) return emptyList()

        val newsArticles = mutableListOf<NewsArticle>()

        response.articles.forEach { articleItem ->
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

    private fun parseDate(publishedAt: String?): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK)
        return try {
            val date = dateFormat.parse(publishedAt)
            DATE_HOUR_FORMAT.get()?.format(date)
        } catch (e: ParseException) {
            null
        }
    }

    companion object DateHelper {
        const val DATE_HOUR_STRING = "dd/MM/yyyy - HH:mm:ss"

        val DATE_HOUR_FORMAT = object : ThreadLocal<DateFormat>() {
            override fun initialValue(): DateFormat {
                return SimpleDateFormat(DATE_HOUR_STRING, Locale.UK)
            }
        }
    }
}