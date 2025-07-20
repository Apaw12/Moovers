package com.example.moovers.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moovers.model.Ticket
import com.example.moovers.user.User
import com.example.moovers.user.UserDAO
import com.example.moovers.ticket.TicketDao

@Database(entities = [User::class, Ticket::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "moovers_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

