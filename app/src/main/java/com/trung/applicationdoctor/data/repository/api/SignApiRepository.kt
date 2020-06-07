package com.trung.applicationdoctor.data.repository.api

import com.trung.applicationdoctor.core.BaseApiResult
import com.trung.applicationdoctor.core.BaseRepository
import com.trung.applicationdoctor.data.remote.api.AppDoctorAPI
import com.trung.applicationdoctor.data.remote.response.SignInInformation

class SignApiRepository(private val appDoctorAPI: AppDoctorAPI) : BaseRepository(){

    suspend fun signIn(memberId: String, memberPw: String, gcmKey: Int, deviceOS: String) : BaseApiResult<SignInInformation> {
        return safeApi {
            appDoctorAPI.signIn(memberId, memberPw, gcmKey, deviceOS)
        }
    }
}