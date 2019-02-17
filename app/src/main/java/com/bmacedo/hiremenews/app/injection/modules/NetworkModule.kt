package com.bmacedo.hiremenews.app.injection.modules

import android.content.res.Resources
import com.bmacedo.hiremenews.BuildConfig
import com.bmacedo.hiremenews.R
import com.bmacedo.hiremenews.app.api.NewsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val httpClient = OkHttpClient.Builder()

        // add logging as last interceptor
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.writeTimeout(1, TimeUnit.MINUTES)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        moshi: Moshi,
        resources: Resources
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(resources.getString(R.string.BASE_URL))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }
}