package com.example.remindmev100.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn


@ExperimentalCoroutinesApi
class CredsRepo(val credsDao: CredsDao) {

    fun readCreds(str: String) : Flow<List<Creds>> = credsDao.readCreds(str).flowOn(Dispatchers.IO).conflate()
    fun searchCreds(search: String) : LiveData<List<Creds>> = credsDao.searchCreds(search)

    suspend fun addCreds(creds: Creds){
        credsDao.addCreds(creds)
    }

    suspend fun updateCreds(creds: Creds){
        credsDao.updateCreds(creds)
    }

    suspend fun deleteDB(){
        credsDao.deleteDB()
    }

    suspend fun updateColor(id: Int, color: Int){
        credsDao.updateColor(id, color)
    }

    suspend fun updatePIN(id: Int, pin: String){
        credsDao.updatePIN(id, pin)
    }

    suspend fun updateNote(id: Int, note: String){
        credsDao.updateNote(id, note)
    }

    suspend fun deleteCreds(id: Int){
        credsDao.deleteCreds(id)
    }

}