package com.trung.applicationdoctor.ui.activity.signin

import android.os.Bundle
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseActivity
import com.trung.applicationdoctor.databinding.ActivityLoginBinding
import com.trung.applicationdoctor.util.extension.setUserEmail
import com.trung.applicationdoctor.util.extension.setUserPW
import com.trung.applicationdoctor.ui.activity.main.MainActivity
import com.trung.applicationdoctor.util.AppDialog
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.setUserMemberIdx
import org.koin.android.viewmodel.ext.android.viewModel


class SignInActivity : BaseActivity<ActivityLoginBinding>(){


    private val viewModel by viewModel(SignInViewModel::class)
    override fun getLayoutResId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.uiEvent.observe(this, onUiEvent())

    }

    /**
     * Function which observe the UI events sent from viewModel to process them
     * LOG_IN_SUCCESS: save user and password to SharePreference, start MainActivity and stop loading on button SignIn
     * IGNORE_LOG_IN: ignore inputting username and password to redirect to MainActivity for Testing APP
     * LOG_IN_FAIL: in case logging in is failed, a dialog is shown to notify the exact error
     */
    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                LOG_IN_SUCCESS -> {
                    context.setUserEmail(viewModel.email.get().toString())
                    context.setUserPW(viewModel.password.get().toString())


                    MainActivity.startActivity(this)
                    viewModel.isLoading.set(false)
                }

                IGNORE_LOG_IN -> {
                    context.setUserEmail("ttt@gmail.com")
                    MainActivity.startActivity(this)
                    viewModel.isLoading.set(false)
                }

                ERROR_EVENT -> {
                    AppDialog.showDialog(this, it.second.toString())
                    viewModel.isLoading.set(false)
                }

                else -> {

                }
            }
        }
    }




    companion object {
        const val LOG_IN_SUCCESS = 0
        const val IGNORE_LOG_IN = 1
    }
}