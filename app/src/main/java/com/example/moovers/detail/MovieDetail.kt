package com.example.moovers.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moovers.R
import com.example.moovers.checkout.SeatSelectionActivity
import com.example.moovers.model.Movie

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val genreList = listOf("Action", "Fantasy", "2h 30m")
        val genreContainer = findViewById<LinearLayout>(R.id.genreContainer)

        genreContainer.removeAllViews()
        for (genre in genreList) {
            val textView = TextView(this)
            textView.text = genre
            textView.setTextColor(ContextCompat.getColor(this, R.color.white_text))
            textView.setBackgroundResource(R.drawable.tag_background)
            textView.setPadding(16, 8, 16, 8)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 16, 0)
            textView.layoutParams = params
            textView.textSize = 12f
            genreContainer.addView(textView)
        }
        // Ambil data dari intent

        val movie = intent.getParcelableExtra<Movie>("MOVIE_DATA")

        if (movie != null) {
            findViewById<ImageView>(R.id.imgPoster).setImageResource(movie.posterResId)
            findViewById<TextView>(R.id.tvTitle).text = movie.title
            findViewById<TextView>(R.id.tvDirector).text = "Director: ${movie.director} ⭐ ${movie.rating}"
            findViewById<TextView>(R.id.tvSynopsis).text = movie.synopsis
        } else {
            Toast.makeText(this, "Data movie tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish() // hindari force close lebih jauh
        }
        findViewById<Button>(R.id.btnBook).setOnClickListener {
            val intent = Intent(this, SeatSelectionActivity::class.java)
            intent.putExtra("MOVIE_DATA", movie)
            startActivity(intent)
        }

        // Tombol kembali
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
