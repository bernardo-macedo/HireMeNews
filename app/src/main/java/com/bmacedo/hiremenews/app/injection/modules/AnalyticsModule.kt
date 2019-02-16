package com.bmacedo.hiremenews.app.injection.modules

import com.bmacedo.hiremenews.app.analytics.CrashlyticsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesCrashlytics() = CrashlyticsManager()

}