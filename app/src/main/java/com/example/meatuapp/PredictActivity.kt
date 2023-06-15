package com.example.meatuapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.meatuapp.databinding.ActivityPredictBinding
import java.io.File

class PredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prediction = intent.getStringExtra("prediction")
        val imagePath = intent.getStringExtra("imagePath")
        Log.d("image", imagePath.toString())
        if (imagePath != null) {
            Glide.with(this)
                .load(File(imagePath))
                .into(binding.imageView2)
        }

        binding.txtHasil.text = prediction.toString()
    }
}