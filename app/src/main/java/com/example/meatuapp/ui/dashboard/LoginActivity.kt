package com.example.meatuapp.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.R
import com.example.meatuapp.Response.LoginResponse
import com.example.meatuapp.ViewModel.LoginViewModel
import com.example.meatuapp.ViewModel.UserViewModelFactory
import com.example.meatuapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginList: LoginResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivLogo.setImageResource(R.drawable.logo)
        val userFactory = UserViewModelFactory.getInstance(this)
        val loginViewModel = ViewModelProvider(this, userFactory)[LoginViewModel::class.java]
        loginViewModel.getUser().observe(this) { userLogin ->
            if (userLogin.nama.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            if (email.isEmpty() == false) {
                if (password.length >= 8) {
                    binding.progressBar.isVisible = true
                    loginViewModel.loginUser(email, password).observe(this) { login ->

                        if (login.message=="Login successful") {

                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            binding.progressBar.isVisible = false
                            Toast.makeText(
                                this@LoginActivity,
                               "email atau password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Password must be 8 character",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Email Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }
}