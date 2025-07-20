package com.example.moovers.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moovers.auth.LoginActivity
import com.example.moovers.session.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var session: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        session = DataStoreManager(this)

        lifecycleScope.launch {
            delay(1500) // Optional splash delay 1.5 detik
            val userId = session.userId.first()

            if (userId == -1) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            }
            finish() // Hapus SplashActivity dari stack
        }
    }
}
