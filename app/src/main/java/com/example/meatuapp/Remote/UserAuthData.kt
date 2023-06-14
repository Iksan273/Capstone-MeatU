package com.example.meatuapp.Remote

import androidx.lifecycle.LiveData
import com.example.meatuapp.Response.LoginResponse
import com.example.meatuapp.Response.RegisterResponse

interface UserAuthData {
    fun registerUser(name: String,alamat :String, email: String, password: String): LiveData<RegisterResponse>
    fun loginUser(email: String,password: String):LiveData<LoginResponse>
    fun getUser(): LiveData<LoginResponse>
    fun deleteUser()
}