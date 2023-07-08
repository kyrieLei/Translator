package com.example.translator_4_17.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.translator_4_17.db.dao.HistoryDao
import com.example.translator_4_17.db.dao.LanguageBookmarksDao
import com.example.translator_4_17.db.data.HistoryItem
import com.example.translator_4_17.db.data.Language

@Database(
    entities = [
        HistoryItem::class,
        Language::class
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    abstract fun languageBookmarksDao(): LanguageBookmarksDao
}