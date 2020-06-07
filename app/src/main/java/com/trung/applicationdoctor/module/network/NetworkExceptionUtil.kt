package com.trung.applicationdoctor.module.network

import retrofit2.HttpException
import java.net.SocketTimeoutException

object NetworkExceptionUtil {


    /**
     * NetworkOfflineException -> Internet not working
     * HttpException -> non-2xx HTTP response
     * SocketTimeoutException -> time out
     * */
    fun error2String(throwable: Throwable?): String {
        return when (throwable) {
            is NoInternetException -> "Internet Offline"
            is HttpException -> throwable.message()
            is SocketTimeoutException -> "Time out"
            else -> throwable?.message ?: "error"
        }
    }

}