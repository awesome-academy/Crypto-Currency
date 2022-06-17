package com.example.cryptocurency.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkListener(private val listener: OnNetworkChangeListener) : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        listener.onNetworkChange(NetworkManager.isAvailable(context))
    }
}
