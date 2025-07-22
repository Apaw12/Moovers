package com.example.moovers.explore

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moovers.R
import com.example.moovers.databinding.ActivityExploreBinding
import com.example.moovers.detail.MovieDetail
import com.example.moovers.home.HomeActivity
import com.example.moovers.home.MovieAdapter
import com.example.moovers.model.Movie
import com.example.moovers.profile.ProfileActivity

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private lateinit var topMovieAdapter: MovieAdapter
    private lateinit var recommendedAdapter: MovieAdapter

    private val nowShowingMovies = listOf(
        Movie(
            "No Time To Die", "2021", 4.5f, R.drawable.no_time_to_die,
            "Cary Joji Fukunaga", "James Bond embarks on a mission to rescue a kidnapped scientist."
        ),
        Movie(
            "Shang-Chi", "2021", 4.8f, R.drawable.shangci_poster,
            "Destin Daniel Cretton", "Shang-Chi confronts his past and the Ten Rings organization."
        )
    )

    private val upcomingMovies = listOf(
        Movie(
            "Doctor Strange 2", "2022", 4.7f, R.drawable.doctor_strange,
            "Sam Raimi", "Doctor Strange faces the multiverse."
        ),
        Movie(
            "Thor: Love and Thunder", "2022", 4.6f, R.drawable.thor_love,
            "Taika Waititi", "Thor teams up with Jane Foster."
        )
    )

    private val recommendedMovies = listOf(
        Movie("Moonlight", "2016", 4.3f, R.drawable.movie1, "Barry Jenkins", "A young man grapples with his identity."),
        Movie("Black Panther", "2018", 4.6f, R.drawable.movie2, "Ryan Coogler", "T'Challa returns to Wakanda."),
        Movie("Avengers", "2012", 4.7f, R.drawable.movie3, "Joss Whedon", "Earth's mightiest heroes assemble.")
    )

    private var currentTopMovies = nowShowingMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupBottomNavigation()
        setupTabs()
        setupSearchToggle()
        setupSearchAction()
    }

    private fun setupRecyclerViews() {
        binding.rvTopMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topMovieAdapter = MovieAdapter(this, nowShowingMovies) { movie ->
            val intent = Intent(this, MovieDetail::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }
        binding.rvTopMovies.adapter = topMovieAdapter

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
        selectNowShowing()

        binding.btnNowShowing.setOnClickListener {
            if (currentTopMovies != nowShowingMovies) {
                selectNowShowing()
            }
        }
        binding.btnUpcoming.setOnClickListener {
            if (currentTopMovies != upcomingMovies) {
                selectUpcoming()
            }
        }
    }

    private fun selectNowShowing() {
        currentTopMovies = nowShowingMovies
        binding.btnNowShowing.setBackgroundColor(resources.getColor(R.color.red))
        binding.btnUpcoming.setBackgroundColor(resources.getColor(R.color.black))
        topMovieAdapter.updateData(nowShowingMovies)
    }

    private fun selectUpcoming() {
        currentTopMovies = upcomingMovies
        binding.btnUpcoming.setBackgroundColor(resources.getColor(R.color.red))
        binding.btnNowShowing.setBackgroundColor(resources.getColor(R.color.black))
        topMovieAdapter.updateData(upcomingMovies)
    }

    private fun setupSearchToggle() {
        binding.searchBar.visibility = View.GONE
        binding.btnSearch.setOnClickListener {
            binding.searchBar.visibility =
                if (binding.searchBar.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchAction() {
        binding.btnDoSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            topMovieAdapter.filterData(query)
            recommendedAdapter.filterData(query)
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
