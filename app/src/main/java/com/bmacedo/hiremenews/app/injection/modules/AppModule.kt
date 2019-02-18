package com.bmacedo.hiremenews.app.injection.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.bmacedo.hiremenews.utils.AppExecutors
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, AnalyticsModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun providesResources(): Resources = application.resources

    @Provides
    @Singleton
    fun providesAppExecutors(): AppExecutors = AppExecutors()
}