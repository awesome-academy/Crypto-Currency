package com.example.cryptocurency.utils.base

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.cryptocurency.R
import com.example.cryptocurency.utils.extension.showNotificationDialog
import com.example.cryptocurency.utils.extension.showSnackBar
import com.example.cryptocurency.utils.extension.showToast
import com.example.cryptocurency.utils.network.NetworkListener
import com.example.cryptocurency.utils.network.OnNetworkChangeListener
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity(), OnNetworkChangeListener {

    private var _binding: VB? = null
    protected val binding get() = _binding
    private val networkListener = NetworkListener(this)
    private var showNetworkStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding?.root)
        initView()
        initData()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(networkListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNetworkChange(isAvailable: Boolean) {
        if (isAvailable) {
            if (showNetworkStatus) {
                val notifyText = "Internet connected"
                binding?.root?.showSnackBar(notifyText)
                showNetworkStatus = false
            }
        } else {
            if (!showNetworkStatus){
                val notifyTitle = "Internet Alert"
                val notifyMassage = "Internet is not available!"
                val retryBtn = "Retry"
                this.showNotificationDialog(notifyTitle, notifyMassage, retryBtn)
                showNetworkStatus = true
            }
        }
    }

    abstract fun initView()
    abstract fun initData()
}
