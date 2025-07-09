package com.example.moovers.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.R
import com.example.moovers.database.BookingDatabaseHelper
import com.example.moovers.model.TicketData

class MyTicket : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: MyTicketAdapter
    private lateinit var dbHelper: BookingDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ticket)

        dbHelper = BookingDatabaseHelper(this)
        val ticketList: List<TicketData> = dbHelper.getAllTickets()

        recyclerView = findViewById(R.id.recyclerTickets)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ticketAdapter = MyTicketAdapter(ticketList)
        recyclerView.adapter = ticketAdapter

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}