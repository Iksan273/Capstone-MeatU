package com.example.meatuapp.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meatuapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val intent = Intent(this, LoginActivity::class.java)
        window.decorView.postDelayed({

            startActivity(intent)
            finish()
        }, 3000)

    }
}