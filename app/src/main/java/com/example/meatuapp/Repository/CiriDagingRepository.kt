package com.example.meatuapp.Repository

import com.example.meatuapp.Data.CiriDaging
import com.example.meatuapp.Data.CiriDagingData
import com.example.meatuapp.Data.JenisDaging
import com.example.meatuapp.Data.JenisDagingData

class CiriDagingRepository {
    private val CiriDagingList= mutableListOf<CiriDaging>()

    init{
        if(CiriDagingList.isEmpty()){
            CiriDagingData.ciriDaging.forEach{
                    ciriDaging ->  CiriDagingList.add(
                CiriDaging(
                    url=ciriDaging.url,
                    title = ciriDaging.title,
                    description = ciriDaging.description

                )
            )
            }
        }
    }

    fun getCiriDaging(): List<CiriDaging>{
        return  (CiriDagingList)
    }

    companion object {
        @Volatile
        private var instance: CiriDagingRepository? = null

        @JvmStatic
        fun getInstance(

        ): CiriDagingRepository =
            instance ?: synchronized(this) {
                instance ?: CiriDagingRepository()
            }.also { instance = it }
    }
}