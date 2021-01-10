package com.example.remindmev100.room

import android.app.Application
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class CredsVM(app: Application) : AndroidViewModel(app) {

    val randomString = ConflatedBroadcastChannel<Pair<String, Int>>(Pair("", 0))
    val repo : CredsRepo = CredsRepo(CredsDatabase.createDB(app).getDao())
    var searchString = MutableLiveData<String>("")
    val readCreds : LiveData<List<Creds>> = randomString.asFlow().flatMapLatest { pair ->
        repo.readCreds(pair.first)
    }.asLiveData()

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