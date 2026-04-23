package com.reyhanfathir.seminarapp.data

import android.content.Context
import com.reyhanfathir.seminarapp.model.Registration
import org.json.JSONArray
import java.util.UUID

object RegistrationRepository {

    private const val PREFS_NAME = "seminar_prefs"
    private const val KEY_REGISTRATIONS = "registrations"

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getAll(context: Context): List<Registration> {
        val raw = prefs(context).getString(KEY_REGISTRATIONS, null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            (0 until arr.length())
                .map { Registration.fromJson(arr.getJSONObject(it)) }
                .sortedByDescending { it.timestamp }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun add(
        context: Context,
        nama: String,
        email: String,
        phone: String,
        gender: String,
        seminar: String
    ): Registration {
        val registration = Registration(
            id = UUID.randomUUID().toString(),
            nama = nama,
            email = email,
            phone = phone,
            gender = gender,
            seminar = seminar,
            timestamp = System.currentTimeMillis()
        )
        val current = getAll(context).toMutableList()
        current.add(0, registration)
        save(context, current)
        return registration
    }

    fun delete(context: Context, id: String) {
        val current = getAll(context).filterNot { it.id == id }
        save(context, current)
    }

    fun count(context: Context): Int = getAll(context).size

    private fun save(context: Context, list: List<Registration>) {
        val arr = JSONArray()
        list.forEach { arr.put(it.toJson()) }
        prefs(context).edit().putString(KEY_REGISTRATIONS, arr.toString()).apply()
    }
}
