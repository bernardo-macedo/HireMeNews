package com.bmacedo.hiremenews.sources

import com.bmacedo.hiremenews.models.domain.NewsSource

sealed class NewsSourcesViewState {
    object Loading : NewsSourcesViewState()
    class Success(val sources: List<NewsSource>) : NewsSourcesViewState()
    class Error(val errorMessage: String) : NewsSourcesViewState()
}