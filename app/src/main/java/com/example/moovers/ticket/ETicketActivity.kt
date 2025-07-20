package com.example.moovers.ticket

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moovers.R
import com.example.moovers.database.AppDatabase
import com.example.moovers.model.Ticket
import kotlinx.coroutines.launch

class ETicketActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var tvMovieTitle: TextView
    private lateinit var tvSeats: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eticket)

        db = AppDatabase.getDatabase(this)

        tvMovieTitle = findViewById(R.id.tvMovieTitle)
        tvSeats = findViewById(R.id.tvSeats)
        tvDateTime = findViewById(R.id.tvDateTime)
        btnBack = findViewById(R.id.btnBack)

        val movieTitle = intent.getStringExtra("MOVIE_TITLE")
        val seatList = intent.getStringExtra("SEAT_LIST")

        if (movieTitle != null && seatList != null) {
            // Data sudah dikirim dari SeatSelectionActivity
            tvMovieTitle.text = movieTitle
            tvSeats.text = "Seats: $seatList"
            tvDateTime.text = "Date: 2025-07-18 | Time: 19:00" // Bisa diganti real time
        } else {
            // Jika mau ambil langsung dari DB (opsional)
            val ticketId = intent.getIntExtra("TICKET_ID", -1)
            if (ticketId != -1) {
                lifecycleScope.launch {
                    val ticket: Ticket? = db.ticketDao().getTicketById(ticketId)
                    ticket?.let {
                        tvMovieTitle.text = it.movieTitle
                        tvSeats.text = "Seats: ${it.seatNumber}"
                        tvDateTime.text = "Date: ${it.bookingDate} | Time: ${it.bookingTime}"
                    }
                }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
