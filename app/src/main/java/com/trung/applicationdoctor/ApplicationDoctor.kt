package com.trung.applicationdoctor

import android.app.Application
import android.content.Context
import com.trung.applicationdoctor.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationDoctor : Application(){
    override fun onCreate() {
        super.onCreate()

        ApplicationDoctor.context = applicationContext

        startKoin {
            androidLogger()
            androidContext(this@ApplicationDoctor)
            // modules
            modules(
                listOf(viewModelModule, roomModule, repositoryModule, apiModule, networkModule)
            )
        }
    }

    companion object {
        lateinit var context: Context
    }
}