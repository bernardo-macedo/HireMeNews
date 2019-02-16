package com.bmacedo.hiremenews.app.injection.components

import android.app.Application
import com.bmacedo.hiremenews.app.base.HireMeNews
import com.bmacedo.hiremenews.app.injection.modules.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main dependency injection component
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindings::class,
        FragmentBindings::class,
        InterfaceBindings::class,
        AnalyticsModule::class]
)
interface AppComponent : AndroidInjector<HireMeNews> {

    fun inject(application: Application)

}