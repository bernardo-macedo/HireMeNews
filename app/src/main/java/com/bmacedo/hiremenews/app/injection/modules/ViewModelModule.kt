package com.bmacedo.hiremenews.app.injection.modules

import android.content.res.Resources
import com.bmacedo.hiremenews.articles.NewsArticlesRepository
import com.bmacedo.hiremenews.articles.NewsArticlesViewModel
import com.bmacedo.hiremenews.sources.NewsSourcesRepository
import com.bmacedo.hiremenews.sources.NewsSourcesViewModel
import com.bmacedo.hiremenews.utils.Executors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun providesNewsSourcesViewModelFactory(
        newsSourcesRepository: NewsSourcesRepository,
        resources: Resources,
        executors: Executors
    ): NewsSourcesViewModel.Factory {
        return NewsSourcesViewModel.Factory(newsSourcesRepository, resources, executors)
    }

    @Provides
    @Singleton
    fun providesNewsArticlesViewModelFactory(
        newsArticlesRepository: NewsArticlesRepository,
        resources: Resources,
        executors: Executors
    ): NewsArticlesViewModel.Factory {
        return NewsArticlesViewModel.Factory(newsArticlesRepository, resources, executors)
    }

}