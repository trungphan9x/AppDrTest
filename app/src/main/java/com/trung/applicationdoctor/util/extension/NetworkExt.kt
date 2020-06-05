package com.trung.applicationdoctor.util.extension

import android.content.Context
import android.net.ConnectivityManager

val Context.isNetworkConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }