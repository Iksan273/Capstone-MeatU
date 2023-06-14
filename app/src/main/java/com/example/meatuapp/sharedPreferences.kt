package com.example.meatuapp

import android.content.Context
import android.content.SharedPreferences
import com.example.meatuapp.Response.LoginResponse

class sharedPreferences (context: Context) {

    private val preferences = context.getSharedPreferences(CODE, Context.MODE_PRIVATE)

    fun setUserLogin(value: LoginResponse) {
        val editor = preferences.edit()
        editor.putString(NAME, value.nama)
        editor.putString(ALAMAT, value.alamat)
        editor.putString(EMAIL, value.email)
        editor.putString(MESSAGE, value.message)
        editor.putInt(USER_ID, value.id)
        editor.apply()
    }

    fun getUserLogin(): LoginResponse {
        return LoginResponse(
            preferences.getString(NAME, "").toString(),
            preferences.getInt(USER_ID, 0),
            preferences.getString(MESSAGE, "").toString(),
            preferences.getString(EMAIL, "").toString(),
            preferences.getString(ALAMAT, "").toString()
        )
    }

    fun deleteUser() {
        val editor = preferences.edit()
        editor.remove(NAME)
        editor.remove(USER_ID)
        editor.remove(MESSAGE)
        editor.remove(EMAIL)
        editor.remove(ALAMAT)
        editor.apply()
    }

    companion object {
        private const val CODE = "preferences_user"
        private const val NAME = "name"
        private const val ALAMAT = "alamat"
        private const val EMAIL = "email"
        private const val USER_ID = "user_id"
        private const val MESSAGE = "message"
        @Volatile
        private var instance: sharedPreferences? = null

        fun getInstance(context: Context): sharedPreferences =
            instance ?: synchronized(this) {
                instance ?: sharedPreferences(context)
            }

    }
}