package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.articles.NewsArticlesFragment
import com.bmacedo.hiremenews.sources.NewsSourcesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract fun contributesNewsSourcesFragment(): NewsSourcesFragment

    @ContributesAndroidInjector
    abstract fun contributesNewsArticlesFragment(): NewsArticlesFragment

}