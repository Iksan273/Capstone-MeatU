package com.example.meatuapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meatuapp.Remote.UserAuth
import com.example.meatuapp.Remote.UserAuthData
import com.example.meatuapp.Response.RegisterResponse

class UserRepository (private val userAuth: UserAuth,

) : UserAuthData {
    override fun registerUser(
        name: String,
        alamat: String,
        email: String,
        password: String
    ): LiveData<RegisterResponse> {
        return userAuth.registerUser(name, alamat, email, password)
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            userAuth: UserAuth,

        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userAuth)
            }.also { instance = it }
    }
}