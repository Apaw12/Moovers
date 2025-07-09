package com.example.moovers.checkout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.moovers.R
import com.example.moovers.database.BookingDatabaseHelper
import com.example.moovers.model.Movie
import com.example.moovers.ticket.ETicketActivity

class SeatSelectionActivity : AppCompatActivity() {

    private val selectedSeats = mutableListOf<String>() // simpan kursi yg dipilih
    private lateinit var dbHelper: BookingDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        val movie = intent.getParcelableExtra<Movie>("MOVIE_DATA")
        val tvMovieTitle = findViewById<TextView>(R.id.tvMovieTitle)
        val gridLayout = findViewById<GridLayout>(R.id.gridSeats)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        dbHelper = BookingDatabaseHelper(this)

        // Set judul film
        tvMovieTitle.text = movie?.title ?: "Movie Title"

        // Ukuran grid
        val rows = 5
        val cols = 6

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val seatCode = "${'A' + i}${j + 1}"
                val button = Button(this).apply {
                    text = seatCode
                    textSize = 12f
                    setPadding(0, 0, 0, 0)

                    if (dbHelper.isSeatBooked(seatCode, movie?.title ?: "")) {
                        isEnabled = false
                        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_outline))
                    } else {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray))
                        setTextColor(Color.WHITE)

                        setOnClickListener {
                            if (selectedSeats.contains(seatCode)) {
                                selectedSeats.remove(seatCode)
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.dark_gray
                                    )
                                )
                            } else {
                                selectedSeats.add(seatCode)
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.red_button
                                    )
                                )
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



        btnConfirm.setOnClickListener {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Pilih minimal 1 kursi!", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.bookSeats(movie?.title ?: "", selectedSeats)
                val booked = selectedSeats.joinToString(", ")
                generateETicket(movie?.title ?: "", booked)
                finish()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun generateETicket(movieTitle: String, seatInfo: String) {
        val message = """
        🎟️ Tiket Anda 🎟️

        Film: $movieTitle
        Kursi: $seatInfo

        Tunjukkan e-ticket ini ke petugas bioskop.
        Selamat menonton!
    """.trimIndent()

        val intent = Intent(this, ETicketActivity::class.java).apply {
            putExtra("TICKET_MESSAGE", message)
        }
        startActivity(intent)
    }
}



