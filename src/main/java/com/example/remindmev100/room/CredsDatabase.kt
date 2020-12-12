package com.example.remindmev100.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Creds::class])
abstract class CredsDatabase : RoomDatabase() {
    abstract fun getDao() : CredsDao

    companion object{
        var instance: CredsDatabase? = null
        fun createDB(con: Context) : CredsDatabase {
            if (instance != null) {
                return instance as CredsDatabase
            }
            synchronized(this){
                instance = Room.databaseBuilder(con, CredsDatabase::class.java, "creds_db").build()
                return instance  as CredsDatabase
            }
        }
    }
}