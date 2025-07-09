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

        // Set selected item agar ikon Profile aktif
        binding.bottomNavigation.selectedItemId = R.id.nav_profile

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Bottom Navigation listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is HomeActivity) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish() // optional: agar tidak menumpuk activity
                    }
                    true
                }

                R.id.nav_explore -> {
                    if (this !is ExploreActivity) {
                        startActivity(Intent(this, ExploreActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    true
                }

                R.id.nav_profile -> {
                    // Sudah di ProfileActivity, tidak perlu pindah
                    true
                }

                else -> false
            }
        }
    }
}
