package com.bmacedo.hiremenews.app.base

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDex
import com.bmacedo.hiremenews.app.analytics.CrashMonitor
import com.bmacedo.hiremenews.app.injection.components.AppComponent
import com.bmacedo.hiremenews.app.injection.components.DaggerAppComponent
import com.bmacedo.hiremenews.app.injection.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class HireMeNews : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var crashMonitor: CrashMonitor

    @set:VisibleForTesting
    lateinit var component: AppComponent

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = androidInjector

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
        initCrashMonitor()
    }

    private fun initDependencyInjection() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        component.inject(this)
    }

    private fun initCrashMonitor() {
        crashMonitor.init()
    }

}