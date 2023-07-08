package com.example.translator_4_17.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.translator_4_17.db.data.HistoryItem

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryItem")
    fun getAll(): List<HistoryItem>

    @Insert
    fun insertAll(vararg historyItems: HistoryItem)

    @Delete
    fun delete(historyItem: HistoryItem)

    @Query("DELETE FROM HistoryItem")
    fun deleteAll()
}