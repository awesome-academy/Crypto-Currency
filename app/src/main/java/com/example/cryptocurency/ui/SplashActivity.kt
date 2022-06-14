package com.example.cryptocurency.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurency.R
import com.example.cryptocurency.utils.SPLASH_TIME

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).apply{
                startActivity(this)
            }
            finish()
        }, SPLASH_TIME)
    }
}
