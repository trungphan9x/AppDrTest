package com.trung.applicationdoctor.ui.activity.splash

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.base.BaseActivity
import com.trung.applicationdoctor.util.UIEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ViewDataBinding>() {
    private val viewModel by viewModel(SplashViewModel::class)
    override fun getLayoutResId() = -1 // none layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onUiEvent(): Observer<UIEvent<Int>> {
        TODO("Not yet implemented")
    }
}