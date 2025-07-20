package com.example.moovers.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE users SET name = :name, password = :password WHERE id = :id")
    suspend fun updateUser(id: Int, name: String, password: String)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

}
