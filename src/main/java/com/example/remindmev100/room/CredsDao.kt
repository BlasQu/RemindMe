package com.example.remindmev100.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CredsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCreds(creds: Creds)

    @Query("SELECT * FROM creds_data where creds_title LIKE '%' || :str || '%'")
    fun readCreds(str: String) : Flow<List<Creds>>

    @Query("DELETE FROM creds_data where creds_id=:id")
    suspend fun deleteCreds(id: Int)

    @Update
    suspend fun updateCreds(creds: Creds)

    @Query("DELETE FROM creds_data")
    suspend fun deleteDB()

    @Query("UPDATE creds_data SET creds_color=:color WHERE creds_id=:id")
    suspend fun updateColor(id: Int, color: Int)

    @Query("UPDATE creds_data SET creds_pin=:pin WHERE creds_id=:id")
    suspend fun updatePIN(id: Int, pin: String)

    @Query("UPDATE creds_data SET creds_note=:note WHERE creds_id=:id")
    suspend fun updateNote(id: Int, note: String)

    @Query("SELECT * FROM creds_data where creds_title LIKE '%' || :search || '%' or creds_login LIKE '%' || :search || '%'")
    fun searchCreds(search: String) : LiveData<List<Creds>>
}