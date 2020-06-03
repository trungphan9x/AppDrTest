package com.trung.applicationdoctor.di

import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(AppDoctorAPI::class.java) }
}