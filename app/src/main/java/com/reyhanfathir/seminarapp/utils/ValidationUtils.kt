package com.reyhanfathir.seminarapp.utils

import android.util.Patterns

object ValidationUtils {

    fun validateName(name: String): String? = when {
        name.isBlank() -> "Nama tidak boleh kosong"
        name.length < 2 -> "Nama minimal 2 karakter"
        else -> null
    }

    fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Email tidak boleh kosong"
        !email.contains("@") -> "Email harus mengandung '@'"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Format email tidak valid"
        else -> null
    }

    fun validatePhone(phone: String): String? = when {
        phone.isBlank() -> "Nomor HP tidak boleh kosong"
        !phone.all { it.isDigit() } -> "Nomor HP hanya boleh berisi angka"
        phone.length !in 10..13 -> "Nomor HP harus 10–13 digit"
        !phone.startsWith("08") -> "Nomor HP harus diawali dengan 08"
        else -> null
    }
}
