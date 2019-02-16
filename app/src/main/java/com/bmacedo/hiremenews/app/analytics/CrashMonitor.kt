package com.bmacedo.hiremenews.app.analytics

interface CrashMonitor {

    fun init()

    fun logException(exception: Throwable)

}