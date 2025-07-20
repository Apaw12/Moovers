package com.example.moovers.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.R
import com.example.moovers.database.AppDatabase
import com.example.moovers.model.Ticket
import com.example.moovers.session.DataStoreManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyTicket : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: MyTicketAdapter
    private lateinit var db: AppDatabase
    private lateinit var session: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ticket)

        db = AppDatabase.getDatabase(this)
        session = DataStoreManager(this)

        recyclerView = findViewById(R.id.recyclerTickets)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ticketAdapter = MyTicketAdapter(emptyList()) { ticket ->
            showDeleteDialog(ticket)
        }
        recyclerView.adapter = ticketAdapter

        lifecycleScope.launch {
            val userId = session.userId.first()
            db.ticketDao().getTicketsByUser(userId).collectLatest { tickets ->
                ticketAdapter.updateTickets(tickets)
            }
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun showDeleteDialog(ticket: Ticket) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Tiket")
            .setMessage("Apakah kamu yakin ingin menghapus tiket untuk film ${ticket.movieTitle}?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    db.ticketDao().delete(ticket)
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
