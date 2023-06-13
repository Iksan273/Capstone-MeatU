package com.example.meatuapp.Remote

import androidx.lifecycle.LiveData
import com.example.meatuapp.Response.RegisterResponse

interface UserAuthData {
    fun registerUser(name: String,alamat :String, email: String, password: String): LiveData<RegisterResponse>
}