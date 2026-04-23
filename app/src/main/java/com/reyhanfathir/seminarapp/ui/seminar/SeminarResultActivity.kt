package com.reyhanfathir.seminarapp.ui.seminar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reyhanfathir.seminarapp.R
import com.reyhanfathir.seminarapp.databinding.ActivitySeminarResultBinding
import com.reyhanfathir.seminarapp.ui.home.MainActivity

class SeminarResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeminarResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeminarResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayResult()

        binding.btnKembali.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(MainActivity.EXTRA_TAB, R.id.menu_laporan)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun displayResult() {
        binding.tvNamaValue.text = intent.getStringExtra(EXTRA_NAMA) ?: "-"
        binding.tvEmailValue.text = intent.getStringExtra(EXTRA_EMAIL) ?: "-"
        binding.tvPhoneValue.text = intent.getStringExtra(EXTRA_PHONE) ?: "-"
        binding.tvGenderValue.text = intent.getStringExtra(EXTRA_GENDER) ?: "-"
        binding.tvSeminarValue.text = intent.getStringExtra(EXTRA_SEMINAR) ?: "-"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(MainActivity.EXTRA_TAB, R.id.menu_laporan)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_ID = "ID"
        const val EXTRA_NAMA = "NAMA"
        const val EXTRA_EMAIL = "EMAIL"
        const val EXTRA_PHONE = "PHONE"
        const val EXTRA_GENDER = "GENDER"
        const val EXTRA_SEMINAR = "SEMINAR"
    }
}
