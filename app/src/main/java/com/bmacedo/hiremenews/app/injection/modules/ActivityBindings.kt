package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.sources.SourceListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {

    @ContributesAndroidInjector
    abstract fun contributesSourceListActivity(): SourceListActivity

}