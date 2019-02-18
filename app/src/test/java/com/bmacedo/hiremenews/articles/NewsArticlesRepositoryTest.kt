package com.bmacedo.hiremenews.articles

import android.content.res.Resources
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.app.injection.modules.AppModule
import com.bmacedo.hiremenews.models.api.article.NewsArticleItemResponse
import com.bmacedo.hiremenews.models.api.article.NewsArticleListResponse
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.models.mappers.NewsArticleMapper
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

class NewsArticlesRepositoryTest {

    @Rule
    @JvmField
    var rxRule = RxImmediateRule()

    private val api: NewsApi = mock()
    private val resources: Resources = mock()
    private val moshi: Moshi = AppModule(mock()).providesMoshi()
    private val mapper = NewsArticleMapper()

    @Test
    fun getArticlesFromSource_whenValidRequest_ReturnsArticleList() {
        whenever(resources.getString(any())).thenReturn("valid value")
        whenever(api.getArticles(any(), any(), any())).thenReturn(
            Single.just(
                NewsArticleListResponse(
                    articles = listOf(
                        NewsArticleItemResponse(
                            author = "Bernardo",
                            title = "Coinbase users can now withdraw Bitcoin",
                            description = "If you’re a Coinbase user, you may have seen some new tokens on your account.",
                            url = "http://techcrunch.com/2019/02/15/coinbase-users-can-now-withdraw-bitcoin-sv-following-bch-fork/",
                            urlToImage = "https://techcrunch.com/wp-content/uploads/2017/08/bitcoin-split-2017a.jpg?w=711",
                            publishedAt = "2019-02-15T14:53:40Z",
                            content = null
                        )
                    )
                )
            )
        )

        val testRepository = NewsArticlesRepository(api, resources, moshi, mapper)

        testRepository.getArticlesFromSource("test Source", 1).test()
            .assertValue(
                listOf(
                    NewsArticle(
                        author = "Bernardo",
                        title = "Coinbase users can now withdraw Bitcoin",
                        description = "If you’re a Coinbase user, you may have seen some new tokens on your account.",
                        url = "http://techcrunch.com/2019/02/15/coinbase-users-can-now-withdraw-bitcoin-sv-following-bch-fork/",
                        urlToImage = "https://techcrunch.com/wp-content/uploads/2017/08/bitcoin-split-2017a.jpg?w=711",
                        publishedAt = "15/02/2019 - 14:53:40",
                        content = null
                    )
                )
            )
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
        val httpException = HttpException(Response.error<NewsArticleListResponse>(400, errorResponse))
        whenever(resources.getString(any())).thenReturn("invalid value")
        whenever(api.getArticles(any(), any(), any())).thenReturn(Single.error(httpException))

        val testRepository = NewsArticlesRepository(api, resources, moshi, mapper)

        testRepository.getArticlesFromSource("test Source", 1).test()
            .assertErrorMessage("Your API key is missing.")
    }

}