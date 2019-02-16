package com.bmacedo.hiremenews.models

import java.util.*

data class NewsArticle(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: Date?,
    val content: String?
)