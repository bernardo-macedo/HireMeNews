package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.app.analytics.CrashMonitor
import com.bmacedo.hiremenews.app.analytics.CrashlyticsManager
import com.bmacedo.hiremenews.utils.AppExecutors
import com.bmacedo.hiremenews.utils.Executors
import dagger.Binds
import dagger.Module

@Module
abstract class InterfaceBindings {

    @Binds
    abstract fun bindCrashMonitor(crashlytics: CrashlyticsManager): CrashMonitor

    @Binds
    abstract fun bindExecutors(appExecutors: AppExecutors): Executors
}