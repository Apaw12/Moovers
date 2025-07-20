package com.example.moovers.checkout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.moovers.R
import com.example.moovers.database.AppDatabase
import com.example.moovers.model.Movie
import com.example.moovers.model.Ticket
import com.example.moovers.session.DataStoreManager
import com.example.moovers.ticket.ETicketActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SeatSelectionActivity : AppCompatActivity() {

    private val selectedSeats = mutableListOf<String>()
    private lateinit var db: AppDatabase
    private lateinit var session: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        val movie = intent.getParcelableExtra<Movie>("MOVIE_DATA")
        val tvMovieTitle = findViewById<TextView>(R.id.tvMovieTitle)
        val gridLayout = findViewById<GridLayout>(R.id.gridSeats)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        db = AppDatabase.getDatabase(this)
        session = DataStoreManager(this)

        // Set judul film
        tvMovieTitle.text = movie?.title ?: "Movie Title"

        val rows = 5
        val cols = 6

        // Ambil kursi yang sudah dibooking untuk film ini
        lifecycleScope.launch {
            val bookedSeats = db.ticketDao().getBookedSeats(movie?.title ?: "")

            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    val seatCode = "${'A' + i}${j + 1}"
                    val button = Button(this@SeatSelectionActivity).apply {
                        text = seatCode
                        textSize = 12f
                        setPadding(0, 0, 0, 0)

                        if (bookedSeats.contains(seatCode)) {
                            isEnabled = false
                            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_outline))
                        } else {
                            setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray))
                            setTextColor(Color.WHITE)

                            setOnClickListener {
                                if (selectedSeats.contains(seatCode)) {
                                    selectedSeats.remove(seatCode)
                                    setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray))
                                } else {
                                    selectedSeats.add(seatCode)
                                    setBackgroundColor(ContextCompat.getColor(context, R.color.red_button))
                                }
                            }
                        }
                    }

                    val params = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(j, 1f)
                        rowSpec = GridLayout.spec(i)
                        setMargins(8, 8, 8, 8)
                    }
                    gridLayout.addView(button, params)
                }
            }
        }

        // Tombol Konfirmasi
        btnConfirm.setOnClickListener {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Pilih minimal 1 kursi!", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val userId = session.userId.first()
                    if (userId == -1) {
                        Toast.makeText(this@SeatSelectionActivity, "User tidak valid, login ulang!", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    val ticket = Ticket(
                        userId = userId,
                        movieTitle = movie?.title ?: "",
                        seatNumber = selectedSeats.joinToString(", "),
                        bookingDate = "2025-07-18", // Bisa pakai tanggal real-time
                        bookingTime = "19:00" // Bisa pakai jam real-time
                    )

                    db.ticketDao().insert(ticket)

                    // Setelah simpan, pindah ke e-ticket
                    val intent = Intent(this@SeatSelectionActivity, ETicketActivity::class.java).apply {
                        putExtra("MOVIE_TITLE", movie?.title)
                        putExtra("SEAT_LIST", selectedSeats.joinToString(", "))
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Tombol Back
        btnBack.setOnClickListener { finish() }
    }
}
