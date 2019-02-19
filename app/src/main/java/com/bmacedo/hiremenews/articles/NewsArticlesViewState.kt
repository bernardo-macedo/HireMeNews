package com.bmacedo.hiremenews.articles

import com.bmacedo.hiremenews.models.domain.NewsArticle

sealed class NewsArticlesViewState {
    data class Success(
        val isLoading: Boolean = false,
        val articles: List<NewsArticle> = emptyList()
    ) : NewsArticlesViewState()

    data class Error(val errorMessage: String) : NewsArticlesViewState()
}