package com.trung.applicationdoctor.di

import com.trung.applicationdoctor.ui.activity.detail.DetailViewModel
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.ui.activity.signin.SignInViewModel
import com.trung.applicationdoctor.ui.fragment.list.ListChannelViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { Dispatchers.IO }
    viewModel { MainViewModel( get(), get() , get()) }
    viewModel { DetailViewModel(get(), get(), get()) }
    viewModel { ListChannelViewModel(get(), get(), get()) }
    viewModel { SignInViewModel(get() , get()) }
}