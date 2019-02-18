package com.bmacedo.hiremenews.articles

import android.content.res.Resources
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.utils.AppExecutors
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import kotlin.test.assertEquals

class NewsArticlesViewModelTest {

    private val repository: NewsArticlesRepository = mock()
    private val resources: Resources = mock()
    private val executors = AppExecutors(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())

    private val viewModel = NewsArticlesViewModel(repository, resources, executors)

    @Test
    fun getArticlesFromSource_onInit_setStateToLoadingThenFinish() {
        val result: List<NewsArticle> = mock()
        whenever(repository.getArticlesFromSource(any(), any())).thenReturn(Single.just(result))

        val testObserver = viewModel.viewState().test()

        viewModel.getArticlesFromSource("testSource")

        val values = testObserver.values()

        assertEquals(NewsArticlesViewState.Loading, values[0])
        assertEquals(result, (values[1] as NewsArticlesViewState.Success).articles)
    }

    @Test
    fun getArticlesFromSource_onError_setStateToLoadingThenError() {
        whenever(
            repository.getArticlesFromSource(
                any(),
                any()
            )
        ).thenReturn(Single.error(RuntimeException("test message")))

        val testObserver = viewModel.viewState().test()

        viewModel.getArticlesFromSource("testSource")

        val values = testObserver.values()

        assertEquals(NewsArticlesViewState.Loading, values[0])
        assertEquals("test message", (values[1] as NewsArticlesViewState.Error).errorMessage)
    }

}