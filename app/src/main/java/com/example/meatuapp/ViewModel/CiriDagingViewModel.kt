package com.example.meatuapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meatuapp.Data.CiriDaging
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.Repository.CiriDagingRepository
import com.example.meatuapp.Repository.JenisDagingRepository

class CiriDagingViewModel (private val ciriDagingRepository: CiriDagingRepository): ViewModel() {
    private val _listCiriDaging= MutableLiveData<List<CiriDaging>>()
    val listCiriDaging: LiveData<List<CiriDaging>> = _listCiriDaging

    init{
        getCiriDaging()
    }

    fun getCiriDaging(){
        _listCiriDaging.value=ciriDagingRepository.getCiriDaging()
    }
}