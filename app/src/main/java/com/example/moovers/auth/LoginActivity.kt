package com.example.moovers.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moovers.database.AppDatabase
import com.example.moovers.databinding.ActivityLoginBinding
import com.example.moovers.home.HomeActivity
import com.example.moovers.session.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase
    private lateinit var session: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        session = DataStoreManager(this)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email & Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val user = db.userDao().loginUser(email, password)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        lifecycleScope.launch {
                            session.saveUserId(user.id)
                            Log.d("SESSION_SAVE", "Saved userId: ${user.id}")
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Berhasil!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
