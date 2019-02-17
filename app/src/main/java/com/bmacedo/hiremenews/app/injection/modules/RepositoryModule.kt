package com.bmacedo.hiremenews.app.injection.modules

import android.content.res.Resources
import com.bmacedo.hiremenews.app.api.NewsApi
import com.bmacedo.hiremenews.articles.NewsArticlesRepository
import com.bmacedo.hiremenews.models.mappers.NewsArticleMapper
import com.bmacedo.hiremenews.models.mappers.NewsSourceMapper
import com.bmacedo.hiremenews.sources.NewsSourcesRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsSourcesRepository(moshi: Moshi, api: NewsApi, resources: Resources) =
        NewsSourcesRepository(api, resources, moshi, NewsSourceMapper())

    @Singleton
    @Provides
    fun providesNewsArticlesRepository(moshi: Moshi, api: NewsApi, resources: Resources) =
        NewsArticlesRepository(api, resources, moshi, NewsArticleMapper())
}