package com.trung.applicationdoctor.ui.activity.signin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseActivity
import com.trung.applicationdoctor.databinding.ActivityLoginBinding
import com.trung.applicationdoctor.extension.setUserEmail
import com.trung.applicationdoctor.ui.activity.main.MainActivity
import com.trung.applicationdoctor.util.UIEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInActivity : BaseActivity<ActivityLoginBinding>(){


    private val viewModel by viewModel(SignInViewModel::class)
    private lateinit var alertDialog : AlertDialog.Builder
    override fun getLayoutResId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alertDialog = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.uiEvent.observe(this, onUiEvent())

    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                LOG_IN_SUCCESS -> {
                    context.setUserEmail(viewModel.email.get().toString())

                    MainActivity.startActivity(this)
                }

                LOG_IN_FAIL -> {
                    alertDialog.setMessage(it.second.toString())
                    alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
                    alertDialog.show()
                }

                else -> {

                }
            }
        }
    }




    companion object {
        const val LOG_IN_SUCCESS = 0
        const val LOG_IN_FAIL = 1
    }
}