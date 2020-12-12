package com.example.remindmev100.room

import androidx.lifecycle.LiveData
import androidx.room.*


class CredsRepo(val credsDao: CredsDao) {

    val readCreds : LiveData<List<Creds>> = credsDao.readCreds()
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