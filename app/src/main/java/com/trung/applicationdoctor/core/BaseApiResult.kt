package com.trung.applicationdoctor.core

data class BaseApiResult<out T>(
    val data: T? = null,
    val errorString: String? = null
) {
}