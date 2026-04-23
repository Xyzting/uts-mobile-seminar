package com.reyhanfathir.seminarapp.model

import org.json.JSONObject

data class Registration(
    val id: String,
    val nama: String,
    val email: String,
    val phone: String,
    val gender: String,
    val seminar: String,
    val timestamp: Long
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("nama", nama)
        put("email", email)
        put("phone", phone)
        put("gender", gender)
        put("seminar", seminar)
        put("timestamp", timestamp)
    }

    companion object {
        fun fromJson(json: JSONObject): Registration = Registration(
            id = json.getString("id"),
            nama = json.getString("nama"),
            email = json.getString("email"),
            phone = json.getString("phone"),
            gender = json.getString("gender"),
            seminar = json.getString("seminar"),
            timestamp = json.getLong("timestamp")
        )
    }
}
