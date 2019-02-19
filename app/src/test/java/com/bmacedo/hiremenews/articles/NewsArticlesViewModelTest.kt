package com.bmacedo.hiremenews.articles

import android.content.res.Resources
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
    fun loadArticles_onError_setStateToLoadingThenError() {
        whenever(
            repository.getArticlesFromSource(
                any(),
                any()
            )
        ).thenReturn(Single.error(RuntimeException("test message")))

        val testObserver = viewModel.viewState().test()

        viewModel.init("testSource")
        viewModel.loadArticles()

        val values = testObserver.values()

        assertEquals(true, (values[0] as NewsArticlesViewState.Success).isLoading)
        assertEquals("test message", (values[1] as NewsArticlesViewState.Error).errorMessage)
    }

}