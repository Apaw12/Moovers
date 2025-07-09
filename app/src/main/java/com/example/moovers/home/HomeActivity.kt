package com.example.moovers.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moovers.R
import com.example.moovers.databinding.ActivityHomeBinding
import com.example.moovers.explore.ExploreActivity
import com.example.moovers.model.Movie
import com.example.moovers.model.Cinema
import com.example.moovers.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var movies: List<Movie>
    private lateinit var cinemas: List<Cinema>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set selected nav item to Home
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("USERNAME", "User")
        binding.tvWelcome.text = "Welcome back," + " " + username

        // Setup Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Sudah di Home, tidak perlu pindah
                    true
                }
                R.id.nav_explore -> {
                    if (this !is ExploreActivity) {
                        startActivity(Intent(this, ExploreActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish() // Optional: menghindari penumpukan Activity
                    }
                    true
                }
                R.id.nav_profile -> {
                    if (this !is ProfileActivity) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}
