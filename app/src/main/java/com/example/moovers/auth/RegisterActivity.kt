package com.example.moovers.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moovers.databinding.ActivityRegisterBinding
import com.example.moovers.auth.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val existingEmail = sharedPref.getString("email", null)

            if (existingEmail == email) {
                Toast.makeText(this, "Email sudah terdaftar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("name", name)
                    putString("email", email)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
