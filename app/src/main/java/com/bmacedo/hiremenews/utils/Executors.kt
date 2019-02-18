package com.bmacedo.hiremenews.utils

import io.reactivex.Scheduler

interface Executors {

    fun diskIO(): Scheduler

    fun networkIO(): Scheduler

    fun mainThread(): Scheduler
}