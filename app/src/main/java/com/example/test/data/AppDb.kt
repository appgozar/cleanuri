package com.example.test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LinkItem::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase(){
    abstract fun linkDao(): LinkDao

    companion object{
        @JvmStatic
        @Volatile
        private var instance: AppDb? = null

        @JvmStatic
        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDb {
            return Room.databaseBuilder(context, AppDb::class.java, "main_db").build()
        }
    }
}