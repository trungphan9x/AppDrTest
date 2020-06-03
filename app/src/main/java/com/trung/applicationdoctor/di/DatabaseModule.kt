package com.trung.applicationdoctor.di

import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single {
        AppDoctorDatabase.getInstance(androidApplication())
    }

    single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelCategoryDao() }
    single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelDetailDao() }
    single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelListDao() }
}