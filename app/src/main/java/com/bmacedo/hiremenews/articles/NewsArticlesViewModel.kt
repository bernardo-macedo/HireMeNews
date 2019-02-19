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
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.BehaviorSubject

class NewsArticlesViewModel(
    private val newsArticlesRepository: NewsArticlesRepository,
    private val resources: Resources,
    private val executors: Executors
) : AutoDisposeViewModel() {

    /**
     * Returns the index of the list item last visible
     */
    var lastVisibleItem: Int = 0
    /**
     * Returns the number of items already loaded
     */
    var totalItemCount: Int = 0

    private var pageNumber = 1
    private val paginator = PublishProcessor.create<Int>()
    private val viewRelay = BehaviorSubject.create<NewsArticlesViewState>()
    private var lastState: NewsArticlesViewState = NewsArticlesViewState.Success(isLoading = true)

    /**
     * Initializes event stream for pagination of articles for given source.
     */
    fun init(sourceId: String) {
        paginator.onBackpressureDrop()
            .subscribeOn(executors.networkIO())
            .doOnNext { setLoadingState() }
            .flatMap { page ->
                newsArticlesRepository.getArticlesFromSource(sourceId, page)
                    .subscribeOn(executors.networkIO())
                    .toFlowable()
            }
            .observeOn(executors.mainThread())
            .autoDisposable(this)
            .subscribe(this::onNewsArticlesSuccess) { e -> onNewsSourcesError(e) }
    }

    /**
     * Returns the view state observable
     */
    fun viewState(): Flowable<NewsArticlesViewState> = viewRelay.toFlowable(BackpressureStrategy.DROP)
        .doOnNext { state -> lastState = state }

    /**
     * Requests more articles
     */
    fun loadArticles() {
        paginator.onNext(pageNumber)
    }

    /**
     * Returns true if should load next page
     */
    fun shouldLoadNextPage(): Boolean {
        val state = lastState
        val isLoading = (state is NewsArticlesViewState.Success) && state.isLoading
        return !isLoading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD
    }

    /**
     * Updates the page number
     */
    fun loadNextPageOfArticles() {
        pageNumber++
        loadArticles()
    }

    /**
     * Resets to the first page. Used to forcefully reload the list.
     */
    fun resetArticles() {
        pageNumber = 1
        loadArticles()
    }

    private fun setLoadingState() {
        val state = lastState
        when (state) {
            is NewsArticlesViewState.Success -> viewRelay.onNext(state.copy(isLoading = true))
            is NewsArticlesViewState.Error -> viewRelay.onNext(lastState)
        }
    }

    private fun onNewsArticlesSuccess(articles: List<NewsArticle>) {
        val updatedArticles = appendWithPreviousPages(articles)
        viewRelay.onNext(
            NewsArticlesViewState.Success(
                isLoading = false,
                articles = updatedArticles
            )
        )
    }

    private fun appendWithPreviousPages(articles: List<NewsArticle>): List<NewsArticle> {
        val state = lastState
        val previousArticles = if (state is NewsArticlesViewState.Success && pageNumber > 1) {
            state.articles
        } else {
            mutableListOf()
        }
        return previousArticles + articles
    }

    private fun onNewsSourcesError(e: Throwable) {
        val errorMessage = e.message ?: resources.getString(R.string.generic_error_message)
        viewRelay.onNext(NewsArticlesViewState.Error(errorMessage))
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 1
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