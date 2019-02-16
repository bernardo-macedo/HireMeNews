package com.bmacedo.hiremenews.app.analytics

import com.crashlytics.android.Crashlytics

class CrashlyticsManager : CrashMonitor {

    override fun init() {
        // No op
    }

    override fun logException(exception: Throwable) {
        Crashlytics.logException(exception)
    }
}