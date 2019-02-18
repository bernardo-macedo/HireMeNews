package com.bmacedo.hiremenews.sources

import android.content.res.Resources
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.app.injection.modules.AppModule
import com.bmacedo.hiremenews.models.api.source.NewsSourceItemResponse
import com.bmacedo.hiremenews.models.api.source.NewsSourceListResponse
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.models.mappers.NewsSourceMapper
import com.bmacedo.hiremenews.testhelpers.RxImmediateRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class NewsSourcesRepositoryTest {

    @Rule
    @JvmField
    var rxRule = RxImmediateRule()

    private val api: NewsApi = mock()
    private val resources: Resources = mock()
    private val moshi: Moshi = AppModule(mock()).providesMoshi()
    private val mapper = NewsSourceMapper()

    @Test
    fun getNewsSources_whenValidRequest_ReturnsSourceList() {
        whenever(resources.getString(any())).thenReturn("valid value")
        whenever(api.getNewsSources(any())).thenReturn(
            Single.just(
                NewsSourceListResponse(
                    sources = listOf(
                        NewsSourceItemResponse(id = "bbc", name = "BBC", description = "BBC Network", url = "bbc.com")
                    )
                )
            )
        )

        val testRepository = NewsSourcesRepository(api, resources, moshi, mapper)

        testRepository.getNewsSources().test()
            .assertValue(listOf(NewsSource(id = "bbc", name = "BBC", description = "BBC Network", url = "bbc.com")))
    }

    @Test
    fun getNewsSources_whenInvalidApiKey_ThrowsException() {
        val errorBody = """
            {
                "status": "error",
                "code": "apiKeyMissing",
                "message": "Your API key is missing."
            }
            """
        val errorResponse = ResponseBody.create(MediaType.get("application/json"), errorBody)
        val httpException = HttpException(Response.error<NewsSourceListResponse>(400, errorResponse))
        whenever(resources.getString(any())).thenReturn("invalid value")
        whenever(api.getNewsSources(any())).thenReturn(Single.error(httpException))

        val testRepository = NewsSourcesRepository(api, resources, moshi, mapper)

        testRepository.getNewsSources().test()
            .assertErrorMessage("Your API key is missing.")
    }

}