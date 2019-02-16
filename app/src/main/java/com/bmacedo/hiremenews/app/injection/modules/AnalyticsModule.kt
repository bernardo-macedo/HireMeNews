package com.bmacedo.hiremenews.app.injection.modules

import android.content.Context
import com.bmacedo.hiremenews.app.analytics.Crashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesCrashlytics(context: Context) = Crashlytics(context)

}