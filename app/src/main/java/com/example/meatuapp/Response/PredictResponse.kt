package com.example.meatuapp.Response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predict")
	val predict: String
)
