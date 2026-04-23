package com.reyhanfathir.seminarapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.reyhanfathir.seminarapp.databinding.ActivityLoginBinding
import com.reyhanfathir.seminarapp.model.SeminarData
import com.reyhanfathir.seminarapp.ui.home.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTextWatchers()
        setupClickListeners()
    }

    private fun setupTextWatchers() {
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) binding.tilUsername.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) binding.tilPassword.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            var isValid = true

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                isValid = false
            }
            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                isValid = false
            }
            if (!isValid) return@setOnClickListener

            if (username == SeminarData.VALID_USERNAME && password == SeminarData.VALID_PASSWORD) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USERNAME", username)
                }
                startActivity(intent)
                finish()
            } else {
                Snackbar.make(binding.root, "Username atau password salah!", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
