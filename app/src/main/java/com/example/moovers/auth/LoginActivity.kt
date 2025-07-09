package com.example.moovers.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moovers.databinding.ActivityLoginBinding
import com.example.moovers.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val inputEmail = binding.emailInputLayout.editText?.text.toString()
            val inputPassword = binding.passwordInputLayout.editText?.text.toString()
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val savedEmail = sharedPref.getString("email", null)
            val savedPassword = sharedPref.getString("password", null)
            val savedName = sharedPref.getString("name", "User")
            val editor = sharedPref.edit()



            if (inputEmail == savedEmail && inputPassword == savedPassword) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                // Simpan nama user ke SharedPreferences
                editor.putString("USERNAME", savedName) // Tambahkan ini!
                editor.apply()

                // Mulai HomeActivity tanpa perlu kirim intent kalau mau ambil dari SharedPreferences
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
