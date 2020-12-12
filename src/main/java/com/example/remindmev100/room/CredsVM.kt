package com.example.remindmev100.room

import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CredsVM(app: Application) : AndroidViewModel(app) {

    val repo : CredsRepo
    var searchString = MutableLiveData<String>("")
    var readCreds : LiveData<List<Creds>>

    init {
        val credsDao = CredsDatabase.createDB(app).getDao()
        repo = CredsRepo(credsDao)
        readCreds = Transformations.switchMap(searchString){
            if (TextUtils.isEmpty(it)){
                repo.readCreds
            } else {
                repo.searchCreds(it)
            }
        }
    }

    fun setSearch(search: String){
        searchString.value = search
    }

    fun addCreds(creds: Creds){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addCreds(creds)
        }
    }

    fun deleteCreds(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteCreds(id)
        }
    }

    fun updateCreds(creds: Creds){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateCreds(creds)
        }
    }

    fun updatePIN(id: Int, pin: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updatePIN(id, pin)
        }
    }

    fun updateNote(id: Int, note: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNote(id, note)
        }
    }

    fun deleteDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteDB()
        }
    }

    fun updateColor(id: Int, color: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateColor(id, color)
        }
    }

}