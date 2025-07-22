package com.example.moovers.explore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovers.R
import com.example.moovers.databinding.ActivityExploreBinding
import com.example.moovers.detail.MovieDetail
import com.example.moovers.model.Movie
import com.example.moovers.profile.ProfileActivity
import com.example.moovers.home.HomeActivity
import com.example.moovers.home.MovieAdapter

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private lateinit var topMovieAdapter: MovieAdapter
    private lateinit var recommendedAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupBottomNavigation()
        setupTabs()
    }

    private fun setupRecyclerViews() {
        // Top Movies List
        val topMovies = listOf(
            Movie(
                "No Time To Die", "2021", 4.5f,
                R.drawable.no_time_to_die,
                "Cary Joji Fukunaga",
                "James Bond embarks on a mission to rescue a kidnapped scientist."
            ),
            Movie(
                "Shang - Chi", "2021", 4.8f,
                R.drawable.shangci_poster,
                "Destin Daniel Cretton",
                "Shang-Chi confronts his past and the Ten Rings organization."
            )
        )

        // Recommended Movies
        val recommendedMovies = listOf(
            Movie("Shang-Chi", "2021", 4.8f, R.drawable.shangci_poster, "Destin Daniel Cretton", "Shang-Chi confronts his past linked to the Ten Rings organization."),
            Movie("Resident Evil", "2010", 4.9f, R.drawable.resident_evil_poster, "Christopher Nolan", "A mind-bending thriller about dreams and reality."),
            Movie("Spiderman", "2014", 5.0f, R.drawable.spiderman_poster, "Christopher Nolan", "A journey through space and time to save humanity.")
        )

        // Top Movies RecyclerView
        binding.rvTopMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topMovieAdapter = MovieAdapter(this, topMovies) { movie ->
            val intent = Intent(this, MovieDetail::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }
        binding.rvTopMovies.adapter = topMovieAdapter

        // Recommended Movies RecyclerView
        binding.rvRecommended.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedAdapter = MovieAdapter(this, recommendedMovies) { movie ->
            val intent = Intent(this, MovieDetail::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }
        binding.rvRecommended.adapter = recommendedAdapter
    }

    private fun setupTabs() {
        binding.btnNowShowing.setOnClickListener {
            binding.btnNowShowing.setBackgroundResource(R.drawable.bg_btn_selected)
            binding.btnUpcoming.setBackgroundResource(R.drawable.bg_btn_unselected)
        }
        binding.btnUpcoming.setOnClickListener {
            binding.btnUpcoming.setBackgroundResource(R.drawable.bg_btn_selected)
            binding.btnNowShowing.setBackgroundResource(R.drawable.bg_btn_unselected)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_explore
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_explore -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
