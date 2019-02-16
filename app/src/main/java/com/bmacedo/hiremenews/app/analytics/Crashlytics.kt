package com.bmacedo.hiremenews.app.analytics

import android.content.Context
import io.fabric.sdk.android.Fabric

class Crashlytics(private val context: Context) : CrashMonitor {

    override fun init() {
        Fabric.with(context, com.crashlytics.android.Crashlytics())
    }

    override fun logException(exception: Throwable) {
        com.crashlytics.android.Crashlytics.logException(exception)
    }
}