package com.example.translator_4_17.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Language(
    @PrimaryKey(autoGenerate = false)
    val code: String = "",
    val name: String = ""
)