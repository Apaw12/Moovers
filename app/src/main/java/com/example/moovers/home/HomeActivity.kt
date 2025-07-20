package com.example.moovers.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovers.R
import com.example.moovers.database.AppDatabase
import com.example.moovers.databinding.ActivityHomeBinding
import com.example.moovers.detail.MovieDetail
import com.example.moovers.explore.ExploreActivity
import com.example.moovers.model.Movie
import com.example.moovers.profile.ProfileActivity
import com.example.moovers.session.DataStoreManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.moovers.auth.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var db: AppDatabase
    private lateinit var session: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        session = DataStoreManager(this)

        lifecycleScope.launch {
            session.userId.collect { userId ->
                if (userId != -1) {
                    val user = db.userDao().getUserById(userId)
                    binding.tvWelcome.text = "Welcome, ${user?.name ?: "User"}"
                }
            }
        }



        // Setup RecyclerView
        binding.recyclerMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val movieList = listOf(
            Movie("Shang-Chi", "2021", 4.8f, R.drawable.shangci_poster, "Destin Daniel Cretton", "Shang-Chi confronts his past linked to the Ten Rings organization."),
            Movie("Resident Evil", "2010", 4.9f, R.drawable.resident_evil_poster, "Christopher Nolan", "A mind-bending thriller about dreams and reality."),
            Movie("Spiderman", "2014", 5.0f, R.drawable.spiderman_poster, "Christopher Nolan", "A journey through space and time to save humanity.")
        )

        movieAdapter = MovieAdapter(this, movieList) { movie ->
            val intent = Intent(this, MovieDetail::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }
        binding.recyclerMovies.adapter = movieAdapter

        // Bottom Navigation
        binding.bottomNavigation.selectedItemId = R.id.nav_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_explore -> {
                    startActivity(Intent(this, ExploreActivity::class.java))
                    finish()
                    true
                }
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
