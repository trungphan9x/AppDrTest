package com.trung.applicationdoctor.ui.activity.signin

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseViewModel
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
import com.trung.applicationdoctor.ui.activity.signin.SignInActivity.Companion.LOG_IN_FAIL
import com.trung.applicationdoctor.ui.activity.signin.SignInActivity.Companion.LOG_IN_SUCCESS
import com.trung.applicationdoctor.util.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel(private val signApiRepository: SignApiRepository) : BaseViewModel(), EmailTextInputViewModel, PasswordTextInputViewModel {
    override val email = ObservableField<String>("")
    override val isEmailFocused = ObservableBoolean(true)
    override val isForceEmailError = ObservableBoolean(false)
    override val password = ObservableField<String>("")
    override val isPasswordFocused = ObservableBoolean(false)
    override val isForcePasswordError= ObservableBoolean(false)

    val isLogInClicked = ObservableBoolean(false)
    val isLoading = ObservableBoolean(false)

    /**
     * Input window focus change detection listener
     */
    fun getOnChangeFocused(): View.OnFocusChangeListener {
        return View.OnFocusChangeListener { view, hasFocus ->
            when (view.id) {
                R.id.tiet_sign_email -> {
                    isEmailFocused.set(hasFocus)
                }
                R.id.tiet_sign_password -> {
                    isPasswordFocused.set(hasFocus)
                }
            }
        }
    }


    fun clickLogIn(view: View){
        val email = email.get() ?: return
        val password = password.get() ?: return

        try {
            isLoading.set(true)
            viewModelScope.launch(Dispatchers.IO) {
                signApiRepository.signIn(memberId = email, memberPw = password, deviceOS = "A", gcmKey = 2).let {
                    when(it.code) {
                        "1000" -> _uiEvent.postValue(UIEvent(LOG_IN_SUCCESS, it.codeMsg))
                        "-1" -> _uiEvent.postValue(UIEvent(LOG_IN_FAIL, it.codeMsg))
                    }
                }
            }
        } catch (ex: Exception) {
            _uiEvent.postValue(UIEvent(LOG_IN_FAIL, ex.stackTrace))
        } finally {
            isLoading.set(false)
        }


    }
}