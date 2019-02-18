package com.bmacedo.hiremenews.app

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

inline fun <reified T : Fragment> createFakeFragmentInjector(crossinline block: T.() -> Unit)
        : DispatchingAndroidInjector<Fragment> {
    val injector = AndroidInjector<Fragment> { instance ->
        if (instance is T) {
            instance.block()
        }
    }
    val factory = AndroidInjector.Factory<Fragment> { injector }
    val map = mapOf(Pair<Class<*>, Provider<Factory<*>>>(T::class.java, Provider { factory }))
    val mapString = mapOf(Pair<String, Provider<Factory<*>>>(T::class.toString(), Provider { factory }))
    return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map, mapString)
}