package com.example.moovers.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moovers.model.TicketData

class BookingDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "BookingDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE bookings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                movieTitle TEXT,
                seatCode TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS bookings")
        onCreate(db)
    }

    fun isSeatBooked(seatCode: String, movieTitle: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM bookings WHERE seatCode = ? AND movieTitle = ?",
            arrayOf(seatCode, movieTitle)
        )
        val isBooked = cursor.count > 0
        cursor.close()
        return isBooked
    }

    fun bookSeats(movieTitle: String, seatList: List<String>) {
        val db = writableDatabase
        for (seat in seatList) {
            val values = ContentValues().apply {
                put("movieTitle", movieTitle)
                put("seatCode", seat)
            }
            db.insert("bookings", null, values)
        }
    }

    fun getAllTickets(): List<TicketData> {
        val db = readableDatabase
        val list = mutableListOf<TicketData>()
        val cursor = db.rawQuery("SELECT * FROM bookings", null)
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("movieTitle"))
            val seat = cursor.getString(cursor.getColumnIndexOrThrow("seatCode"))
            // Bisa pakai dummy waktu sementara
            list.add(TicketData(title, seat, "2025-07-09", "19:00"))
        }
        cursor.close()
        return list
    }


    fun deleteAll() {
        writableDatabase.execSQL("DELETE FROM bookings")
    }
}
