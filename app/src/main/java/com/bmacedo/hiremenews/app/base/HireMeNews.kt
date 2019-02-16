package com.bmacedo.hiremenews.app.base

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.bmacedo.hiremenews.app.analytics.CrashMonitor
import com.bmacedo.hiremenews.app.injection.components.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class HireMeNews : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var crashMonitor: CrashMonitor

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
        DaggerAppComponent.create()
            .inject(this)
    }

    private fun initCrashMonitor() {
        crashMonitor.init()
    }

}