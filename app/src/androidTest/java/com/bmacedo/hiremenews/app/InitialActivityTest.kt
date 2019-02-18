package com.bmacedo.hiremenews.app


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.RecyclerViewMatcher.Companion.withRecyclerView
import com.bmacedo.hiremenews.app.base.HireMeNews
import com.bmacedo.hiremenews.articles.NewsArticlesRepository
import com.bmacedo.hiremenews.models.domain.NewsArticle
import com.bmacedo.hiremenews.models.domain.NewsSource
import com.bmacedo.hiremenews.sources.NewsSourcesFragment
import com.bmacedo.hiremenews.sources.NewsSourcesRepository
import com.bmacedo.hiremenews.sources.NewsSourcesViewModel
import com.bmacedo.hiremenews.utils.AppExecutors
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InitialActivityTest {

    private val sourcesRepository: NewsSourcesRepository = mock()

    private val articlesRepository: NewsArticlesRepository = mock()

    @get:Rule
    val activityTestRule = object : ActivityTestRule<InitialActivity>(InitialActivity::class.java, true, false) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as HireMeNews
            val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
            val testExecutors = AppExecutors(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())
            app.fragmentDispatchingAndroidInjector = createFakeFragmentInjector<NewsSourcesFragment> {
                viewModelFactory = NewsSourcesViewModel.Factory(sourcesRepository, resources, testExecutors)
                executors = testExecutors
            }
        }
    }

    @Before
    fun setUp() {
        val testSources = listOf(
            NewsSource(
                id = "abc-news",
                name = "ABC News Mock",
                description = "Your trusted source for breaking news.",
                url = "https://abcnews.go.com"
            ),
            NewsSource(
                id = "abc-news-au",
                name = "ABC News (AU)",
                description = "Australia's most trusted source of local, national and world news.",
                url = "http://www.abc.net.au/news"
            )
        )
        whenever(sourcesRepository.getNewsSources()).thenReturn(Single.just(testSources))

        val testArticles = listOf(
            NewsArticle(
                author = "Bernardo",
                title = "Coinbase users can now withdraw Bitcoin",
                description = "If you’re a Coinbase user, you may have seen some new tokens on your account.",
                url = "http://techcrunch.com/2019/02/15/coinbase-users-can-now-withdraw-bitcoin-sv-following-bch-fork/",
                urlToImage = "https://techcrunch.com/wp-content/uploads/2017/08/bitcoin-split-2017a.jpg?w=711",
                publishedAt = "2019-02-15",
                content = null
            )
        )

        whenever(articlesRepository.getArticlesFromSource(any())).thenReturn(Single.just(testArticles))
    }

    @Test
    fun initialActivity_onLaunch_showsSourcesWithDataFromRepository() {
        activityTestRule.launchActivity(null)

        onView(withRecyclerView(R.id.news_sources_list).atPosition(0))
            .check(matches(hasDescendant(withText("ABC News Mock"))))
            .check(matches(hasDescendant(withText("Your trusted source for breaking news."))))

        onView(withRecyclerView(R.id.news_sources_list).atPosition(1))
            .check(matches(hasDescendant(withText("ABC News (AU)"))))
            .check(matches(hasDescendant(withText("Australia's most trusted source of local, national and world news."))))
    }

}
