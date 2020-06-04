package com.trung.applicationdoctor.ui.activity.signin

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

interface PasswordTextInputViewModel {
    val password: ObservableField<String>
    val isPasswordFocused: ObservableBoolean
    val isForcePasswordError: ObservableBoolean

    /**
     * Function to detect password inputting
     */
    val passwordTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isForcePasswordError.set(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }


    /**
     * 비밀번호가 유효한지 검사해주는 함수
     * 길이가 6 이상이면 유효
     */
    fun checkPasswordValid(password: String?): Boolean {
        return if (password.isNullOrBlank()) false
        else password.length > 5
    }
}