package com.example.moovers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val movieTitle: String,
    val seatNumber: String,
    val bookingDate: String,
    val bookingTime: String
)

