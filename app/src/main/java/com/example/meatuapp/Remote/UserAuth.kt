package com.example.meatuapp.Remote

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meatuapp.API.ApiConfig
import com.example.meatuapp.Model.UserModel
import com.example.meatuapp.Response.RegisterResponse
import com.google.gson.Gson


import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAuth {
    fun registerUser(name: String, alamat: String, email: String, password: String): LiveData<RegisterResponse> {
        val registerResponse = MutableLiveData<RegisterResponse>()
        val user = UserModel(name, alamat, email, password)
        Log.d(TAG, user.toString())

        val gson = Gson()
        val requestBody = gson.toJson(user).toRequestBody("application/json".toMediaTypeOrNull())

        val client = ApiConfig.getApiService().register(requestBody)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                Log.d("response", response.body().toString())
                if (response.isSuccessful) {

                    registerResponse.value = response.body()
                } else {
                    val registerError = RegisterResponse(
                        "",
                        "Registration failed!",
                        "null@gmail.com",
                        "null"
                    )
                    Log.e(TAG, "onFailure: ${response.message()}")
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

    //    fun registerUser(callback: RegisterCallback, name: String, alamat:String, email: String, password: String) {
//        val user = UserModel(name,alamat,email,password)
//        Log.d(TAG, user.toString())
//
//        val gson = Gson()
//        val requestBody = gson.toJson(user).toRequestBody("application/json".toMediaTypeOrNull())
//
//        val client = ApiConfig.getApiService().register(requestBody)
//        client.enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(
//                call: Call<RegisterResponse>,
//                response: Response<RegisterResponse>
//            ) {
//                if (response.isSuccessful) {
//                    Log.d(TAG, response.toString())
//                    response.body()?.let { callback.onRegister(it) }
//                } else {
//                    val registerError = RegisterResponse(
//                        "",
//                        "Registration failed!",
//                        "null@gmail.com",
//                        "null"
//
//                    )
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                    callback.onRegister(
//                        registerError
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                val registerError = RegisterResponse(
//                    "",
//                    t.message.toString(),
//                    "null@gmail.com",
//                    "null"
//                )
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//                callback.onRegister(
//                    registerError
//                )
//            }
//
//        })
//    }
//    interface RegisterCallback {
//        fun onRegister(registerResponse: RegisterResponse)
//
//
//    }
    companion object {
        private const val TAG = "UserAuth"

        @Volatile
        private var instance: UserAuth? = null

        fun getInstance(): UserAuth =
            instance ?: synchronized(this) {
                instance ?: UserAuth()
            }
    }
}