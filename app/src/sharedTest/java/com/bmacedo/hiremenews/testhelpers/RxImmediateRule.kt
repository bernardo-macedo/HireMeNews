package com.bmacedo.hiremenews.testhelpers

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxImmediateRule : TestRule {

    private val scheduler = TestScheduler()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.reset()
                RxJavaPlugins.setComputationSchedulerHandler { scheduler }
                base?.evaluate()
                RxJavaPlugins.reset()
            }
        }
    }
}