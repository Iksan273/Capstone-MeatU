package com.example.meatuapp.API

import com.example.meatuapp.Response.PredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService2 {
    @Multipart
    @POST("/predict")
    fun Analyze(
        @Part file: MultipartBody.Part
    ): Call<PredictResponse>
}