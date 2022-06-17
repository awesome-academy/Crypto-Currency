package com.example.cryptocurency.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkManager {
    fun isAvailable(context: Context?): Boolean {
        try {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val capabilities =
                connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities?.apply {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } catch (e: Exception) {
        }
        return false
    }
}
