package com.example.meatuapp.API

import com.example.meatuapp.Response.LoginResponse
import com.example.meatuapp.Response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/register")
    fun register(@Body requestBody: RequestBody): Call<RegisterResponse>
    @Headers("Content-Type: application/json")
    @POST("/login")
    fun login(@Body requestBody: RequestBody): Call<LoginResponse>
}