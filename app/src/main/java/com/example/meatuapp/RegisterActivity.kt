package com.example.meatuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.ViewModel.RegisterViewModel
import com.example.meatuapp.ViewModel.UserViewModelFactory
import com.example.meatuapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userFactory = UserViewModelFactory.getInstance(this)
        val registerViewModel = ViewModelProvider(this, userFactory)[RegisterViewModel::class.java]



        binding.btnRegister.setOnClickListener {

            var name=binding.etFullname.text.toString()
            var alamat=binding.etAlamat.text.toString()
            var email=binding.etEmail.text.toString()
            var password=binding.etPassword.text.toString()
            if (name.isEmpty() == false && email.isEmpty() == false) {
                if (password.length >= 8) {
                    binding.progressBar.isVisible = true
                    registerViewModel.registerUser(name,alamat, email, password)
                        .observe(this) { register ->
                            Log.d("register", register.message)
                            if (register.message=="Registration successful") {

                                binding.progressBar.isVisible = false
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Register Success, silahkan login",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                binding.progressBar.isVisible = false
                                Toast.makeText(
                                    this@RegisterActivity,
                                    register.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password anda harus terdiri dari 8 karakter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Nama dan Email tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }
}