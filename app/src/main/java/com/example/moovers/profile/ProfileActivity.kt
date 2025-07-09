package com.example.moovers.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moovers.R
import com.example.moovers.auth.LoginActivity
import com.example.moovers.databinding.ActivityProfileBinding
import com.example.moovers.explore.ExploreActivity
import com.example.moovers.home.HomeActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan nama user dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("USERNAME", "User")
        binding.tvProfileName.text = username

        // Tombol logout
        binding.btnLogout.setOnClickListener {
            sharedPref.edit().remove("USERNAME").apply() // Hanya hapus status login
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        // Bottom navigation
        binding.bottomNavigation.selectedItemId = R.id.nav_profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                    true
                }
                R.id.nav_explore -> {
                    startActivity(Intent(this, ExploreActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
