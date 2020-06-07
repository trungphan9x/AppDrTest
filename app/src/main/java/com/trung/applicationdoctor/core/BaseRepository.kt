package com.trung.applicationdoctor.core

import com.trung.applicationdoctor.module.network.InternetManager
import com.trung.applicationdoctor.module.network.NetworkExceptionUtil
import com.trung.applicationdoctor.module.network.NoInternetException
import com.trung.applicationdoctor.module.unittets.UnitTestManager

abstract class BaseRepository {
    suspend fun <_Response> safeApi(
        callApi: suspend () -> _Response
    ): BaseApiResult<_Response> {
        try {
            if (!UnitTestManager.enable) {
                if (!InternetManager.available) {
                    throw NoInternetException()
                }
            }
            callApi.invoke().let {response ->
                return BaseApiResult<_Response>(response)
            }
        } catch (ex: Exception) {
            return BaseApiResult<_Response>(null, NetworkExceptionUtil.error2String(ex))
        }
    }
}