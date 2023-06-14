package com.example.meatuapp.Remote

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meatuapp.API.ApiConfig
import com.example.meatuapp.Response.LoginResponse

import com.example.meatuapp.Response.RegisterResponse
import com.example.meatuapp.sharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonObject


import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAuth (private val shared:sharedPreferences) {

    fun registerUser(nama: String, alamat: String, email: String, password: String): LiveData<RegisterResponse> {
        val registerResponse = MutableLiveData<RegisterResponse>()
        val user = JsonObject()
        user.addProperty("nama", nama)
        user.addProperty("alamat", alamat)
        user.addProperty("email", email)
        user.addProperty("password", password)
        Log.d(TAG, user.toString())
        val gson = Gson()
        val requestBody = gson.toJson(user).toRequestBody("application/json".toMediaTypeOrNull())
        Log.d("gson", requestBody.toString())

        val client = ApiConfig.getApiService().register(requestBody)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                Log.d("responseAPI", response.body().toString())
                if (response.isSuccessful) {
                    Log.d(TAG, "Request successful, response code: ${response.code()}")
                    registerResponse.value = response.body()
                } else {
                    val registerError = RegisterResponse(
                        "",
                        "Registration failed!",
                        "",
                        ""
                    )
                    Log.e(TAG, "Request failed, response code: ${response.code()}, message: ${response.message()}")
                    registerResponse.value = registerError
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                val registerError = RegisterResponse(
                    "",
                    t.message.toString(),
                    "null@gmail.com",
                    "null"
                )
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                registerResponse.value = registerError
            }
        })

        return registerResponse
    }
    fun loginUser(email: String, password: String): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        val user = JsonObject()
        user.addProperty("email", email)
        user.addProperty("password", password)
        val gson = Gson()
        val requestBody = gson.toJson(user).toRequestBody("application/json".toMediaTypeOrNull())


        val client = ApiConfig.getApiService().login(requestBody)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("responseAPI", response.body().toString())
                if (response.isSuccessful) {
                    var logindata = response.body()
                    logindata?.let{

                        val loginPreferences=LoginResponse(it.nama,it.id,it.message,it.email,it.alamat)
                        shared.setUserLogin(loginPreferences)
                        loginResponse.value = it

                    }


                } else {
                    Log.e(TAG, "Request failed, response code: ${response.code()}, message: ${response.message()}")
                    val errorResponse = LoginResponse("", 0, "", "", "Login failed!")
                    loginResponse.value = errorResponse
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                val errorResponse = LoginResponse("", 0, "", "", "Login failed!")
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                loginResponse.value = errorResponse
            }
        })

        return loginResponse
    }




    companion object {
        private const val TAG = "UserAuth"

        @Volatile
        private var instance: UserAuth? = null


        fun getInstance(
            sharedPreferences: sharedPreferences
        ): UserAuth =
            instance ?: synchronized(this) {
                instance ?: UserAuth(sharedPreferences)
            }
    }
}