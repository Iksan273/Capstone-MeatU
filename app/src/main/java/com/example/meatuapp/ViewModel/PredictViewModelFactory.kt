package com.example.meatuapp.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.Injection
import com.example.meatuapp.Repository.PredictRepository
import com.example.meatuapp.Repository.UserRepository

class PredictViewModelFactory private constructor(
    private val predictRepository: PredictRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PredictViewModel::class.java) -> {
                PredictViewModel(predictRepository) as T
            }


            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: PredictViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): PredictViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PredictViewModelFactory(Injection.PredictAuthRepository(context))

            }
        }
    }
}