package com.trung.applicationdoctor.data

object Constant {
    const val OkHttp_TIMEOUT = 5L // connection timeout
    const val REQUEST_RETRY_TIME =
        (OkHttp_TIMEOUT + 1) * 1000 // ms - use for retry request api. It must be over OkHttp_TIMEOUT
}