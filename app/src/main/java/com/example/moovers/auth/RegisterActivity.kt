package com.example.moovers.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moovers.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.moovers.user.User
import com.example.moovers.user.UserDAO
import com.example.moovers.database.AppDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existingUser = db.userDao().getUserByEmail(email)
                if (existingUser == null) {
                    db.userDao().registerUser(User(name = name, email = email, password = password))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
