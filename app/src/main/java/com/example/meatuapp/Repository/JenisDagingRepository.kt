package com.example.meatuapp.Repository

import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.Data.JenisDagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class JenisDagingRepository {

    private val JenisDagingList= mutableListOf<JenisDaging>()

    init{
        if(JenisDagingList.isEmpty()){
            JenisDagingData.JenisDaging.forEach{
                jenisDaging ->  JenisDagingList.add(
                    JenisDaging(
                        name = jenisDaging.name,
                        description = jenisDaging.description

                    )
                )
            }
        }
    }

    fun getJenisDaging(): List<JenisDaging>{
        return  (JenisDagingList)
    }

    companion object {
        @Volatile
        private var instance: JenisDagingRepository? = null

        @JvmStatic
        fun getInstance(

        ): JenisDagingRepository =
            instance ?: synchronized(this) {
                instance ?: JenisDagingRepository()
            }.also { instance = it }
    }


}