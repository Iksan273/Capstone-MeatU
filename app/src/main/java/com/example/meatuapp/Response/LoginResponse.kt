package com.example.meatuapp.Response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("LoginResponse")
	val loginResponse: List<LoginResponseItem>
)

data class LoginResponseItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("message")
	val message: String
)
