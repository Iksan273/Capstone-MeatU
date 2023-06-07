package com.example.meatuapp.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.Injection
import com.example.meatuapp.Repository.CiriDagingRepository
import com.example.meatuapp.Repository.JenisDagingRepository

class CiriDagingViewModelFactory (private val ciriDagingRepository: CiriDagingRepository) :
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CiriDagingViewModel::class.java) -> {
                CiriDagingViewModel(ciriDagingRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
    companion object {
        @Volatile
        private var instance: CiriDagingViewModelFactory? = null
        fun getInstance(context: Context): CiriDagingViewModelFactory = instance ?: synchronized(this) {
            instance ?: CiriDagingViewModelFactory(Injection.CiriDagingRepository(context))
        }
    }
    }