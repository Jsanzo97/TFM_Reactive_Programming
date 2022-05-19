package com.example.reactiveprogramming.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reactiveprogramming.ui.home.HomeActivity

/**
 * Show splash view during the application loading, this is manage in the manifest setting the theme for this activity,
 * then start the home activity. By this way we show something to user who is waiting the application initialization
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}