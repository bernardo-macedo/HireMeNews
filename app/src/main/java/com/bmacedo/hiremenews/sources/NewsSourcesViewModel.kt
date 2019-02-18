package com.bmacedo.hiremenews.sources

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.utils.AutoDisposeViewModel
import com.bmacedo.hiremenews.utils.Executors
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class NewsSourcesViewModel(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val resources: Resources,
    private val executors: Executors
) : AutoDisposeViewModel() {

    private val viewRelay = BehaviorSubject.create<NewsSourcesViewState>()

    /**
     * Returns the view state observable
     */
    fun viewState(): Observable<NewsSourcesViewState> = viewRelay.hide()

    /**
     * Retrieves news sources
     */
    fun getNewsSources() {
        if (!viewRelay.hasValue()) {
            viewRelay.onNext(NewsSourcesViewState.Loading)
        }
        newsSourcesRepository.getNewsSources()
            .subscribeOn(executors.networkIO())
            .observeOn(executors.mainThread())
            .autoDisposable(this)
            .subscribe(this::onNewsSourcesSuccess) { e -> onNewsSourcesError(e) }

    }

    private fun onNewsSourcesSuccess(sources: List<NewsSource>) {
        viewRelay.onNext(NewsSourcesViewState.Success(sources))
    }

    private fun onNewsSourcesError(e: Throwable) {
        val errorMessage = e.message ?: resources.getString(R.string.generic_error_message)
        viewRelay.onNext(NewsSourcesViewState.Error(errorMessage))
    }

    class Factory(
        private val newsSourcesRepository: NewsSourcesRepository,
        private val resources: Resources,
        private val executors: Executors
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsSourcesViewModel::class.java)) {
                return NewsSourcesViewModel(newsSourcesRepository, resources, executors) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}