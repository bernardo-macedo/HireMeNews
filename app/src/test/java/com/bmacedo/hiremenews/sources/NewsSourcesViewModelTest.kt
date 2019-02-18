package com.bmacedo.hiremenews.sources

import android.content.res.Resources
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.utils.AppExecutors
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import kotlin.test.assertEquals

class NewsSourcesViewModelTest {

    private val repository: NewsSourcesRepository = mock()
    private val resources: Resources = mock()
    private val executors = AppExecutors(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())

    private val viewModel = NewsSourcesViewModel(repository, resources, executors)

    @Test
    fun getNewsSources_onInit_setStateToLoadingThenFinish() {
        val result: List<NewsSource> = mock()
        whenever(repository.getNewsSources()).thenReturn(Single.just(result))

        val testObserver = viewModel.viewState().test()

        viewModel.getNewsSources()

        val values = testObserver.values()

        assertEquals(NewsSourcesViewState.Loading, values[0])
        assertEquals(result, (values[1] as NewsSourcesViewState.Success).sources)
    }

    @Test
    fun getNewsSources_onError_setStateToLoadingThenError() {
        whenever(repository.getNewsSources()).thenReturn(Single.error(RuntimeException("test message")))

        val testObserver = viewModel.viewState().test()

        viewModel.getNewsSources()

        val values = testObserver.values()

        assertEquals(NewsSourcesViewState.Loading, values[0])
        assertEquals("test message", (values[1] as NewsSourcesViewState.Error).errorMessage)
    }

    @Test
    fun getNewsSources_onSecondTimeCalled_doesNotSetStateToLoading() {
        val result: List<NewsSource> = mock()
        whenever(repository.getNewsSources()).thenReturn(Single.just(result))

        // Calling first time to load resources
        viewModel.getNewsSources()

        // Start observing
        val testObserver = viewModel.viewState().test()

        // Calling second time to check if state becomes loading again (which shouldn't)
        viewModel.getNewsSources()

        val values = testObserver.values()

        assertEquals(2, testObserver.valueCount())
        assertEquals(result, (values[0] as NewsSourcesViewState.Success).sources)
        assertEquals(result, (values[1] as NewsSourcesViewState.Success).sources)
    }
}