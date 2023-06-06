package com.example.meatuapp.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meatuapp.Injection
import com.example.meatuapp.Repository.JenisDagingRepository

class DagingViewModelFactory(private val jenisDagingRepository: JenisDagingRepository ) :
        ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DagingViewModel::class.java) -> {
                DagingViewModel(jenisDagingRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
    companion object {
        @Volatile
        private var instance: DagingViewModelFactory? = null
        fun getInstance(context: Context): DagingViewModelFactory = instance ?: synchronized(this) {
            instance ?: DagingViewModelFactory(Injection.JenisDagingRepository(context))
        }
    }


}