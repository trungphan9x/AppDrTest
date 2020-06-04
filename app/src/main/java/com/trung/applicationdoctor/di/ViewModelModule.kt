package com.trung.applicationdoctor.di

import com.trung.applicationdoctor.ui.activity.detail.DetailViewModel
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.ui.activity.signin.SignInViewModel
import com.trung.applicationdoctor.ui.activity.splash.SplashViewModel
import com.trung.applicationdoctor.ui.fragment.list.ListChannelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel( get(), get(), get() ) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { ListChannelViewModel(get(), get(), get()) }
    viewModel { SplashViewModel() }
    viewModel { SignInViewModel(get() ) }
}