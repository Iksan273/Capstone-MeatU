package com.example.meatuapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.meatuapp.Repository.PredictRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PredictViewModel (private val predictRepository: PredictRepository) :ViewModel(){

    fun Analyze( file: MultipartBody.Part) =
        predictRepository.analyze( file)


}