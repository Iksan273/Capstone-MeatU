package com.example.meatuapp

import android.content.Context
import com.example.meatuapp.Remote.Predict
import com.example.meatuapp.Remote.UserAuth
import com.example.meatuapp.Repository.CiriDagingRepository
import com.example.meatuapp.Repository.JenisDagingRepository
import com.example.meatuapp.Repository.PredictRepository
import com.example.meatuapp.Repository.UserRepository

object Injection {

    fun JenisDagingRepository(context: Context):JenisDagingRepository{
        return JenisDagingRepository.getInstance()
    }
    fun CiriDagingRepository(context: Context):CiriDagingRepository{
        return CiriDagingRepository.getInstance()
    }
    fun UserAuthRepository(context: Context): UserRepository {
        val sharedPreferences = sharedPreferences.getInstance(context)
        val userRemote = UserAuth.getInstance(sharedPreferences)
        return UserRepository.getInstance(userRemote,sharedPreferences)
    }
    fun PredictAuthRepository(context: Context):PredictRepository{
        val predictRemote=Predict.getInstance()
        return PredictRepository(predictRemote)
    }
}