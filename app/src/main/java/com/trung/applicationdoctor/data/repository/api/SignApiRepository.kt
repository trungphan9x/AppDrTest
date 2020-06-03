package com.trung.applicationdoctor.data.repository.api

import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import com.trung.applicationdoctor.data.remote.response.SignInInformation

class SignApiRepository(private val appDoctorAPI: AppDoctorAPI) {

    suspend fun signIn(memberId: String, memberPw: String, gcmKey: Int, deviceOS: String) : SignInInformation {
        return appDoctorAPI.signIn(memberId, memberPw, gcmKey, deviceOS)
    }
}