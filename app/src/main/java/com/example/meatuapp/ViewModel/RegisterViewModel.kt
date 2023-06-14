package com.example.meatuapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.meatuapp.Repository.UserRepository

class RegisterViewModel  (private val userRepository: UserRepository): ViewModel(){
    fun registerUser(name: String,alamat: String, email: String, password: String) =
        userRepository.registerUser(name, alamat,email, password)

}