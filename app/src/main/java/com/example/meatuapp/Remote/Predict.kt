package com.example.meatuapp.Remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.meatuapp.API.ApiConfig2
import com.example.meatuapp.Response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Predict {

    fun analyze(file:MultipartBody.Part) : MutableLiveData<PredictResponse>{
        val predictResponse = MutableLiveData<PredictResponse>()
        val client=ApiConfig2.getApiService().Analyze(file)

        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                Log.d("APIPredict", response.body().toString())
                if (response.isSuccessful) {
                    Log.d(TAG, "Request successful, response code: ${response.code()}")
                    predictResponse.value = response.body()
                } else {
                    val registerError = PredictResponse(
                        "null"
                    )
                    Log.e(TAG, "Request failed, response code: ${response.code()}, message: ${response.message()}")
                    predictResponse.value = registerError
                }
            }


            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                val registerError = PredictResponse(
                    "null"
                )
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                predictResponse.value = registerError
            }
        })
        return predictResponse


    }
    companion object {
        private const val TAG = "PredictAuth"

        @Volatile
        private var instance: Predict? = null


        fun getInstance(

        ): Predict =
            instance ?: synchronized(this) {
                instance ?: Predict()
            }
    }
}