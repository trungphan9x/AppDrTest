package com.trung.applicationdoctor.util

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object AppRequestState {
    fun getErrorString(ex: Throwable): String {
        return if (ex is SocketTimeoutException || ex is IOException) {
            return "Time out"
        } else if (ex is HttpException) {
            return ex.response()?.errorBody()?.source()?.readUtf8()?:ex.toString()
        } else {
            return ex.toString()
        }
    }
}