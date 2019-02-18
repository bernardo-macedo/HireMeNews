package com.bmacedo.hiremenews.app.injection.components

import android.app.Application
import com.bmacedo.hiremenews.app.base.HireMeNews
import com.bmacedo.hiremenews.app.injection.modules.ActivityBindings
import com.bmacedo.hiremenews.app.injection.modules.AppModule
import com.bmacedo.hiremenews.app.injection.modules.FragmentBindings
import com.bmacedo.hiremenews.app.injection.modules.InterfaceBindings
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
        InterfaceBindings::class
    ]
)
interface AppComponent : AndroidInjector<HireMeNews> {

    fun inject(application: Application)

}