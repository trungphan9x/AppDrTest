package com.trung.applicationdoctor.ui.activity.signin

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

interface EmailTextInputViewModel {
    val email: ObservableField<String>
    val isEmailFocused: ObservableBoolean
    val isForceEmailError: ObservableBoolean

    /**
     * Text change detection listener
     */
    val emailTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isForceEmailError.set(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }

    /**
     * Method to check Email Validation
     * @param email : EMAIL to validate
     */
    fun checkEmailValid(email: String?): Boolean {
        return if (email.isNullOrBlank()) false
        else android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}