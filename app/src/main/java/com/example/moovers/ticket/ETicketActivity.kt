package com.example.moovers.ticket

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moovers.R

class ETicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eticket)


        val textView = findViewById<TextView>(R.id.tvTicket)
        textView.text = intent.getStringExtra("TICKET_MESSAGE")
    }
}
