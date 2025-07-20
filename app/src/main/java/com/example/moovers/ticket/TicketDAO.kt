package com.example.moovers.ticket

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moovers.model.Ticket

@Dao
interface TicketDao {
    @Insert
    suspend fun insert(ticket: Ticket)

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    fun getTicketsByUser(userId: Int): kotlinx.coroutines.flow.Flow<List<Ticket>>

    @Query("SELECT seatNumber FROM tickets WHERE movieTitle = :movieTitle")
    suspend fun getBookedSeats(movieTitle: String): List<String>

    @Query("SELECT * FROM tickets WHERE id = :ticketId LIMIT 1")
    suspend fun getTicketById(ticketId: Int): Ticket?


    @Delete
    suspend fun delete(ticket: Ticket)

}

