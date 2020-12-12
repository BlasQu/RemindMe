package com.example.remindmev100.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creds_data")
data class Creds (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "creds_id") val id: Int = 0, @ColumnInfo(name = "creds_title") val title: String, @ColumnInfo(name = "creds_login") val login: String, val password: String, val pin: Boolean = false, val note: Boolean = false, @ColumnInfo(name = "creds_color") val color: Int = 0, @ColumnInfo(name = "creds_pin") val pincontent: String = "", @ColumnInfo(name = "creds_note") val notecontent: String = "")