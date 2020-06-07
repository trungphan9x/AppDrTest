package com.trung.applicationdoctor.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.BuildConfig
import com.trung.applicationdoctor.data.Constant
import com.trung.applicationdoctor.data.db.AppDoctorDatabase
import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import com.trung.applicationdoctor.data.repository.api.ChannelApiRepository
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
import com.trung.applicationdoctor.data.repository.room.ChannelCategoryRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelDetailRoomRepository
import com.trung.applicationdoctor.data.repository.room.ChannelListRoomRepository
import com.trung.applicationdoctor.ui.activity.detail.DetailViewModel
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.ui.activity.signin.SignInViewModel
import com.trung.applicationdoctor.ui.fragment.list.ListChannelViewModel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.java.KoinAndroidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

    val testDispatcher = module {
        factory { TestCoroutineDispatcher() }
    }

    val viewModelModuleAndroidTest = module {
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { DetailViewModel(get(), get(), get()) }
        viewModel { ListChannelViewModel(get(), get(), get()) }
        viewModel { SignInViewModel(get(), get()) }
    }

    val repositoryModuleAndroidTest = module {
        factory { ChannelApiRepository(get()) }
        factory { SignApiRepository(get()) }
        factory { ChannelCategoryRoomRepository(get()) }
        factory { ChannelDetailRoomRepository(get()) }
        factory { ChannelListRoomRepository(get()) }
    }


    val networkModuleAndroidTest = module {
        factory { buildOkHttpClient() }
        single { buildRetrofit(get()) }

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

    val roomModuleAndroidTest = module {

        single {
            // In-Memory database config
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext<Context>(), AppDoctorDatabase::class.java,
                "db"
            ).allowMainThreadQueries().build()
        }


        single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelCategoryDao() }
        single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelDetailDao() }
        single(createdAtStart = false) { get<AppDoctorDatabase>().getChannelListDao() }
    }


    val apiModuleAndroidTest = module {
        single(createdAtStart = false) { get<Retrofit>().create(AppDoctorAPI::class.java) }
    }
