package com.bmacedo.hiremenews.articles

import com.bmacedo.hiremenews.models.domain.NewsArticle

sealed class NewsArticlesViewState {
    object Loading : NewsArticlesViewState()
    class Success(val articles: List<NewsArticle>) : NewsArticlesViewState()
    class Error(val errorMessage: String) : NewsArticlesViewState()
}