package com.example.meatuapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.Repository.JenisDagingRepository

class DagingViewModel(private val jenisDagingRepository: JenisDagingRepository):ViewModel() {

    private val _listJenisDaging=MutableLiveData<List<JenisDaging>>()
    val listJenisDaging:LiveData<List<JenisDaging>> = _listJenisDaging

    init{
        getAllDaging()
    }

    fun getAllDaging(){
        _listJenisDaging.value=jenisDagingRepository.getJenisDaging()
    }





}
