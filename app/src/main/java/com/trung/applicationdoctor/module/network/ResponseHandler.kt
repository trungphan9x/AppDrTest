package com.trung.applicationdoctor.module.network

import com.trung.applicationdoctor.core.BaseApiResult
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {
    fun <T> handleSuccess(data: T): BaseApiResult<T> {
        return BaseApiResult.success(data)
    }

    fun <T> handleException(e: Exception): BaseApiResult<T> {
        return when (e) {
            is HttpException -> BaseApiResult.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> BaseApiResult.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
//            is IOException -> BaseApiResult.error("No internet connection", null)
            else -> BaseApiResult.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong.\nError code: $code"
        }
    }
}
