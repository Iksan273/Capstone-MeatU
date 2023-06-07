package com.example.meatuapp

import android.content.Context
import com.example.meatuapp.Repository.CiriDagingRepository
import com.example.meatuapp.Repository.JenisDagingRepository

object Injection {

    fun JenisDagingRepository(context: Context):JenisDagingRepository{
        return JenisDagingRepository.getInstance()
    }
    fun CiriDagingRepository(context: Context):CiriDagingRepository{
        return CiriDagingRepository.getInstance()
    }
}