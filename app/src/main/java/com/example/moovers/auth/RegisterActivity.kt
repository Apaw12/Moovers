package com.example.moovers.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moovers.databinding.ActivityRegisterBinding
import com.example.moovers.home.HomeActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) // Perbaikan: inflate (huruf kecil)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { // Perbaikan: btnRegister (bukan btmRegister)
            if (validateInput()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        return if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            binding.nameEditText.error = if (name.isEmpty()) "Nama tidak boleh kosong" else null
            binding.emailEditText.error = if (email.isEmpty()) "Email tidak boleh kosong" else null
            binding.passwordEditText.error = if (password.isEmpty()) "Password tidak boleh kosong" else null
            false
        } else {
            true
        }
    }
}