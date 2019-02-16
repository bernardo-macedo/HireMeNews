package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.app.InitialActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {

    @ContributesAndroidInjector
    abstract fun contributesInitialActivity(): InitialActivity

}