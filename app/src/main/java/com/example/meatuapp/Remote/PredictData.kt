package com.example.meatuapp.Remote

import androidx.lifecycle.LiveData
import com.example.meatuapp.Response.PredictResponse
import okhttp3.MultipartBody

interface PredictData {
    fun analyze( file: MultipartBody.Part):
            LiveData<PredictResponse>
}