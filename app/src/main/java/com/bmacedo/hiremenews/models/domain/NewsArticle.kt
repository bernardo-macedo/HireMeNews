package com.bmacedo.hiremenews.models.domain

import android.content.Context
import com.bmacedo.hiremenews.R

data class NewsArticle(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) {

    fun getPublicationInfo(context: Context): String {
        return when {
            publishedAt != null && author != null ->
                context.getString(R.string.news_article_publication_info, publishedAt, author)
            publishedAt != null -> publishedAt
            author != null -> author
            else -> ""
        }
    }

}