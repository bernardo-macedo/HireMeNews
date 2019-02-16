package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.app.analytics.CrashMonitor
import com.bmacedo.hiremenews.app.analytics.Crashlytics
import dagger.Binds
import dagger.Module

@Module
abstract class InterfaceBindings {

    @Binds
    abstract fun bindCrashMonitor(crashlytics: Crashlytics): CrashMonitor
}