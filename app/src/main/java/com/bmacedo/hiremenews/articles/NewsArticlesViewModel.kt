package com.bmacedo.hiremenews.articles

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.utils.AutoDisposeViewModel
import com.bmacedo.hiremenews.utils.Executors
import com.uber.autodispose.autoDisposable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class NewsArticlesViewModel(
    private val newsArticlesRepository: NewsArticlesRepository,
    private val resources: Resources,
    private val executors: Executors
) : AutoDisposeViewModel() {

    private val viewRelay = BehaviorSubject.create<NewsArticlesViewState>()

    /**
     * Returns the view state observable
     */
    fun viewState(): Flowable<NewsArticlesViewState> = viewRelay.toFlowable(BackpressureStrategy.DROP)

    /**
     * Retrieve the articles related to a given source
     */
    fun getArticlesFromSource(sourceId: String, page: Int = 1) {
        viewRelay.onNext(NewsArticlesViewState.Loading)
        newsArticlesRepository.getArticlesFromSource(sourceId, page)
            .subscribeOn(executors.networkIO())
            .observeOn(executors.mainThread())
            .autoDisposable(this)
            .subscribe(this::onNewsArticlesSuccess) { e -> onNewsSourcesError(e) }
    }


    private fun onNewsArticlesSuccess(articles: List<NewsArticle>) {
        viewRelay.onNext(NewsArticlesViewState.Success(articles))
    }

    private fun onNewsSourcesError(e: Throwable) {
        val errorMessage = e.message ?: resources.getString(R.string.generic_error_message)
        viewRelay.onNext(NewsArticlesViewState.Error(errorMessage))
    }

    class Factory(
        private val newsArticlesRepository: NewsArticlesRepository,
        private val resources: Resources,
        private val executors: Executors
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsArticlesViewModel::class.java)) {
                return NewsArticlesViewModel(newsArticlesRepository, resources, executors) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}