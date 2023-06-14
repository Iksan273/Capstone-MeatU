package com.example.meatuapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.meatuapp.databinding.ActivityLoginBinding
import com.example.meatuapp.databinding.ActivityPredictBinding
import java.io.File
import kotlin.math.log

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
            val file = File(imagePath)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            binding.imageView2.setImageBitmap(bitmap)
        }

        binding.txtHasil.setText(prediction.toString())
    }
}