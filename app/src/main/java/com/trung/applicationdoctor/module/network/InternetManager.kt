package com.trung.applicationdoctor.module.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.module.unittets.UnitTestManager
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object InternetManager {

    val available: Boolean
        get() {
            return if (UnitTestManager.enable) {
                true
            } else (isConnectionOn() && isInternetAvailable())
        }

    /**
     * check wifi or cellular open with device android < 23
     * */
    private fun preAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    /**
     * check wifi or cellular open with device android > 23
     * */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val connection =
            connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    /**
     * check wifi or cellular open or close
     * */
    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            ApplicationDoctor.context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                    ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.M
        ) {
            postAndroidMInternetCheck(connectivityManager)
        } else {
            preAndroidMInternetCheck(connectivityManager)
        }
    }


    /**
     * The above just state that is the WiFi or Cellular Data is ON. But that doesn’t guarantee if there’s internet.
     *
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out/27312494#27312494
     * */
    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }


}