package com.example.meatuapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.meatuapp.Repository.UserRepository

class LoginViewModel (private val userRepository: UserRepository)
: ViewModel(){
    fun loginUser( email: String, password: String) =
        userRepository.loginUser(email, password)
    fun getUser() = userRepository.getUser()

}