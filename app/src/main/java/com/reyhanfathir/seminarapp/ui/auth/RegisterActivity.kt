package com.reyhanfathir.seminarapp.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.reyhanfathir.seminarapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        setupTextWatchers()
        setupClickListeners()
    }

    private fun setupTextWatchers() {
        binding.etNama.addTextChangedListener(makeWatcher {
            binding.tilNama.error = if (it.isNullOrBlank()) "Nama tidak boleh kosong" else null
        })
        binding.etEmail.addTextChangedListener(makeWatcher {
            binding.tilEmail.error = when {
                it.isNullOrBlank() -> "Email tidak boleh kosong"
                !it.contains("@") -> "Email harus mengandung '@'"
                else -> null
            }
        })
        binding.etPassword.addTextChangedListener(makeWatcher {
            binding.tilPassword.error = if (it.isNullOrBlank()) "Password tidak boleh kosong" else null
        })
        binding.etKonfirmasi.addTextChangedListener(makeWatcher {
            val pass = binding.etPassword.text.toString()
            binding.tilKonfirmasi.error = when {
                it.isNullOrBlank() -> "Konfirmasi password tidak boleh kosong"
                it.toString() != pass -> "Password tidak cocok"
                else -> null
            }
        })
    }

    private fun makeWatcher(action: (CharSequence?) -> Unit) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { action(s) }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val konfirmasi = binding.etKonfirmasi.text.toString().trim()
            var isValid = true

            if (nama.isEmpty()) { binding.tilNama.error = "Nama tidak boleh kosong"; isValid = false }
            if (email.isEmpty() || !email.contains("@")) { binding.tilEmail.error = "Email tidak valid"; isValid = false }
            if (password.isEmpty()) { binding.tilPassword.error = "Password tidak boleh kosong"; isValid = false }
            if (konfirmasi != password) { binding.tilKonfirmasi.error = "Password tidak cocok"; isValid = false }

            if (!isValid) return@setOnClickListener

            Snackbar.make(binding.root, "Registrasi berhasil! Silakan login.", Snackbar.LENGTH_SHORT).show()
            binding.root.postDelayed({ finish() }, 1500)
        }

        binding.tvLogin.setOnClickListener { finish() }
    }
}
