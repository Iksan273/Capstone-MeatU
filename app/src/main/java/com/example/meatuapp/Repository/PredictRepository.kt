package com.example.meatuapp.Repository

import androidx.lifecycle.LiveData
import com.example.meatuapp.Remote.Predict
import com.example.meatuapp.Remote.PredictData
import com.example.meatuapp.Response.PredictResponse
import okhttp3.MultipartBody


class PredictRepository (private val PredictAuth: Predict):PredictData {
    override fun analyze(file: MultipartBody.Part): LiveData<PredictResponse> {
        return PredictAuth.analyze(file)
    }

    companion object {
        @Volatile
        private var instance: PredictRepository? = null

        @JvmStatic
        fun getInstance(
            predict: Predict,


            ): PredictRepository =
            instance ?: synchronized(this) {
                instance ?: PredictRepository(predict)
            }.also { instance = it }
    }

}