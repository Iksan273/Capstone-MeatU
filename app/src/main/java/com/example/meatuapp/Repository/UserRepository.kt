package com.example.meatuapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meatuapp.Remote.UserAuth
import com.example.meatuapp.Remote.UserAuthData
import com.example.meatuapp.Response.LoginResponse
import com.example.meatuapp.Response.RegisterResponse
import com.example.meatuapp.sharedPreferences

class UserRepository (private val userAuth: UserAuth,
                      private val sharedPreferences: sharedPreferences

) : UserAuthData {
    override fun registerUser(
        name: String,
        alamat: String,
        email: String,
        password: String
    ): LiveData<RegisterResponse> {
        return userAuth.registerUser(name, alamat, email, password)
    }

    override fun loginUser(email: String, password: String): LiveData<LoginResponse> {
       return userAuth.loginUser(email,password)
    }
    override fun getUser(): LiveData<LoginResponse> {
        val user = MutableLiveData<LoginResponse>()
        user.postValue(sharedPreferences.getUserLogin())
        return user
    }

    override fun deleteUser() {
        sharedPreferences.deleteUser()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            userAuth: UserAuth,
            sharedPreferences: sharedPreferences

        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userAuth,sharedPreferences)
            }.also { instance = it }
    }
}