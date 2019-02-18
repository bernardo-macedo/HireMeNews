package com.bmacedo.hiremenews.app.api

import com.bmacedo.hiremenews.models.api.article.NewsArticleListResponse
import com.bmacedo.hiremenews.models.api.source.NewsSourceListResponse
import com.bmacedo.hiremenews.testhelpers.MockWebServerRule
import com.bmacedo.hiremenews.testhelpers.RxImmediateRule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.QueryParam
import io.appflate.restmock.utils.RequestMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsApiTest {

    @Rule
    @JvmField
    var rxRule = RxImmediateRule()

    @Rule
    @JvmField
    var mockWebServerRule = MockWebServerRule()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    lateinit var api: NewsApi

    @Before
    fun setUp() {

        val retrofit = Retrofit.Builder()
            .baseUrl(RESTMockServer.getUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create(NewsApi::class.java)
    }

    @Test
    fun getNewsSources_whenApiProvided_passesProperParameters() {
        val testApiKey = "testKey"
        val queryParam = QueryParam("apiKey", testApiKey)

        val requestMatchers = RequestMatchers.hasExactQueryParameters(queryParam)

        val mock200Response = """
            {
              "status": "ok",
              "sources": [
                {
                  "id": "abc-news",
                  "name": "ABC News",
                  "description": "Your trusted source for breaking news.",
                  "url": "https://abcnews.go.com",
                  "category": "general",
                  "language": "en",
                  "country": "us"
                },
                {
                  "id": "abc-news-au",
                  "name": "ABC News (AU)",
                  "description": "Australia's most trusted source of local, national and world news.",
                  "url": "http://www.abc.net.au/news",
                  "category": "general",
                  "language": "en",
                  "country": "au"
                }
              ]
            }
            """

        RESTMockServer.whenGET(requestMatchers)
            .thenReturnString(200, mock200Response)

        val expectedResult = moshi.adapter(NewsSourceListResponse::class.java).fromJson(mock200Response)

        api.getNewsSources(testApiKey).test()
            .assertValue(expectedResult)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun getArticles_whenApiProvided_passesProperParameters() {
        val testApiKey = "testKey"
        val testSource = "abc-news"
        val testPage = 1
        val apiKeyQueryParam = QueryParam("apiKey", testApiKey)
        val pageQueryParam = QueryParam("page", testPage.toString())
        val sourceQueryParam = QueryParam("sources", testSource)

        val requestMatchers =
            RequestMatchers.hasExactQueryParameters(apiKeyQueryParam, sourceQueryParam, pageQueryParam)

        val mock200Response = """
             {
                "status": "ok",
                "totalResults": 6698,
                "articles": [{
                        "source": {
                            "id": "abc-news",
                            "name": "ABC News"
                        },
                        "author": "ABC News",
                        "title": "WATCH: Victims named in Aurora mass shooting",
                        "description": "The five victims were all employees of the Henry Pratt company in Aurora, Illinois.",
                        "url": "https://abcnews.go.com/US/video/victims-named-aurora-mass-shooting-61121187",
                        "urlToImage": "https://s.abcnews.com/images/US/190216_vod_orig_auroravictimsMIX_hpMain_16x9_992.jpg",
                        "publishedAt": "2019-02-16T18:20:20Z",
                        "content": "Now Playing: Gunman apprehended following active shooter situation in Aurora, Illinois"
                    },
                    {
                        "source": {
                            "id": "abc-news",
                            "name": "ABC News"
                        },
                        "author": "ABC News",
                        "title": "WATCH: Police recount prior arrests of Aurora mass shooter",
                        "description": "The gunman, identified as Gary Martin, 45, was killed by responding police officers.",
                        "url": "https://abcnews.go.com/US/video/police-recount-prior-arrests-aurora-mass-shooter-61121140",
                        "urlToImage": "https://s.abcnews.com/images/US/190216_vod_orig_aurorashooterMIX_hpMain_16x9_992.jpg",
                        "publishedAt": "2019-02-16T18:19:50Z",
                        "content": "Now Playing: Gunman apprehended following active shooter situation in Aurora, Illinois"
                    }
                ]
             }
            """

        RESTMockServer.whenGET(requestMatchers)
            .thenReturnString(200, mock200Response)

        val expectedResult = moshi.adapter(NewsArticleListResponse::class.java).fromJson(mock200Response)

        api.getArticles(testSource, testPage, testApiKey).test()
            .assertValue(expectedResult)
            .assertNoErrors()
            .assertComplete()
    }
}