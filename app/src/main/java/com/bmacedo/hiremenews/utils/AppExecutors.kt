package com.bmacedo.hiremenews.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Global executor pools for the whole application.
 *
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
@Singleton
open class AppExecutors(
    private val diskIO: Scheduler = Schedulers.io(),
    private val networkIO: Scheduler = Schedulers.io(),
    private val mainThread: Scheduler = AndroidSchedulers.mainThread()
) : Executors {

    override fun diskIO(): Scheduler {
        return diskIO
    }

    override fun networkIO(): Scheduler {
        return networkIO
    }

    override fun mainThread(): Scheduler {
        return mainThread
    }

}