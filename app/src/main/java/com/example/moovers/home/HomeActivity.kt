package com.example.moovers.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.R
import com.example.moovers.databinding.ActivityHomeBinding
import com.example.moovers.detail.MovieDetail
import com.example.moovers.explore.ExploreActivity
import com.example.moovers.model.Movie
import com.example.moovers.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerMovies)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Data dummy
        movieList = listOf(
            Movie(
                title = "Shang-Chi",
                releaseDate = "2021",
                rating = 4.8f,
                posterResId = R.drawable.shangci_poster,
                director = "Destin Daniel Cretton",
                synopsis = "Shang-Chi confronts his past linked to the Ten Rings organization."
            ),
            Movie(
                title = "Residet Evil",
                releaseDate = "2010",
                rating = 4.9f,
                posterResId = R.drawable.resident_evil_poster,
                director = "Christopher Nolan",
                synopsis = "A mind-bending thriller about dreams and reality."
            ),
            Movie(
                title = "Spiderman",
                releaseDate = "2014",
                rating = 5.0f,
                posterResId = R.drawable.spiderman_poster,
                director = "Christopher Nolan",
                synopsis = "A journey through space and time to save humanity."
            )
        )

        // Adapter
        movieAdapter = MovieAdapter(this, movieList) { movie ->
            val intent = Intent(this, MovieDetail::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }

        recyclerView.adapter = movieAdapter



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
