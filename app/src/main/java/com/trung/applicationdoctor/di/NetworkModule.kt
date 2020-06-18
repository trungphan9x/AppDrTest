package com.trung.applicationdoctor.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.trung.applicationdoctor.BuildConfig
import com.trung.applicationdoctor.data.Constant
import com.trung.applicationdoctor.module.network.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { buildOkHttpClient() }
    single { buildRetrofit(get()) }
    factory { ResponseHandler() }

}


fun buildOkHttpClient() = OkHttpClient.Builder().apply {
    connectTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
}

fun buildRetrofit(okHttpClient: OkHttpClient.Builder): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient.build())
        .build()
}