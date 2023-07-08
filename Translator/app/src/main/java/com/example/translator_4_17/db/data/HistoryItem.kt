package com.example.translator_4_17.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val sourceLanguageName: String,
    @ColumnInfo val sourceLanguageCode: String,
    @ColumnInfo val targetLanguageName: String,
    @ColumnInfo val targetLanguageCode: String,
    @ColumnInfo val insertedText: String,
    @ColumnInfo val translatedText: String
)