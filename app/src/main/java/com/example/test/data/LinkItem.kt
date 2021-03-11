package com.example.test.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "links")
data class LinkItem(@PrimaryKey(autoGenerate = true) val id : Int = 0, val url : String, val originalUrl : String, val date : Long = Calendar.getInstance().timeInMillis)