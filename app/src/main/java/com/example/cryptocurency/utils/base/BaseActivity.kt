package com.example.cryptocurency.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding?.root)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun initView()
    abstract fun initData()
}
