package com.trung.applicationdoctor.ui.activity.signin


import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trung.applicationdoctor.ApplicationDoctor.Companion.context
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseViewModel
import com.trung.applicationdoctor.data.enum.Status
import com.trung.applicationdoctor.data.remote.response.SignInInformation
import com.trung.applicationdoctor.data.repository.api.SignApiRepository
import com.trung.applicationdoctor.ui.activity.signin.SignInActivity.Companion.IGNORE_LOG_IN
import com.trung.applicationdoctor.ui.activity.signin.SignInActivity.Companion.LOG_IN_SUCCESS
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.UIEvent
import com.trung.applicationdoctor.util.extension.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel(private val defaultDispatcher: CoroutineDispatcher,
                      private val signApiRepository: SignApiRepository) : BaseViewModel(), EmailTextInputViewModel, PasswordTextInputViewModel {
    override val email = ObservableField<String>("")
    override val isEmailFocused = ObservableBoolean(true)
    override val isForceEmailError = ObservableBoolean(false)
    override val password = ObservableField<String>("")
    override val isPasswordFocused = ObservableBoolean(false)
    override val isForcePasswordError= ObservableBoolean(false)

    val isLogInClicked = ObservableBoolean(false)
    val isLoading = ObservableBoolean(false)

    val resultSignIn: MutableLiveData<SignInInformation> = MutableLiveData<SignInInformation>()
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

    /**
     * after the right email, password are inputted and signIn btn is clicked, Internet Connection will be checked
     * If it has internet, Api will be called to validate username and password,
     * ==> if it's valid, redirect to MainActivity, else dialog showing to notify the error
     * If it has no internet, check if it is the 1st time user open the app
     * ==> If it it's 1st time, dialog showing that "You have no internet connection",
     * ==> else if username n password are the same as them of the 1st time inputted, then redirect to MainActivity else dialog showing "Either ID or Password is wrong"
     */
    fun clickLogIn(view: View? = null){
        isLoading.set(true)
        val email = email.get() ?: return
        val password = password.get() ?: return

        try {
            if(context.isNetworkConnected) {
                viewModelScope.launch(defaultDispatcher) {
                    signApiRepository.signIn(memberId = email, memberPw = password, deviceOS = "A", gcmKey = 2).let { baseApiResult ->
                        when (baseApiResult.status) {
                            Status.SUCCESS -> {
                                baseApiResult.data?.let { result ->
                                    resultSignIn.postValue(result)
                                    when(result.code) {
                                        "1000" -> {
                                            _uiEvent.postValue(UIEvent(LOG_IN_SUCCESS, result.codeMsg))
                                            context.setUserMemberIdx(result.memberIdx)
                                        }
                                        else -> _uiEvent.postValue(UIEvent(ERROR_EVENT, result.codeMsg))
                                    }
                                }
                            }

                            Status.ERROR -> {
                                _uiEvent.postValue(UIEvent(ERROR_EVENT, baseApiResult.message))
                            }

                            Status.LOADING -> {

                            }
                        }

                    }
                }
            } else {
                if(context.hasFirstLaunchApp()) {
                    _uiEvent.postValue(UIEvent(ERROR_EVENT, "You have no internet connection"))
                } else {
                    if(email == context.getUserEmail() && password == context.getUserPw()) {
                        _uiEvent.postValue(UIEvent(LOG_IN_SUCCESS))
                    } else {
                        _uiEvent.postValue(UIEvent(ERROR_EVENT, "Either ID or Password is wrong"))
                    }

                }
            }

        } catch (ex: Exception) {
            _uiEvent.postValue(UIEvent(ERROR_EVENT, ex.message))
        } finally {
            view?.hideKeyboard()
        }
    }

    /**
     * click Back btn to ignore the step of login and redirect to MainActivity
     */
    fun clickBackBtn(view: View){
        _uiEvent.postValue(UIEvent(IGNORE_LOG_IN))
    }
}