package com.example.test.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {
    @Query("SELECT * FROM links ORDER BY id DESC LIMIT :offset,:limit")
    fun getLinks(offset : Int, limit : Int): Flow<List<LinkItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinks(links: List<LinkItem>)
}