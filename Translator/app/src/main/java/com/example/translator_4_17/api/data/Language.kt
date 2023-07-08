package com.example.translator_4_17.api.data

import com.example.translator_4_17.db.data.Language


@kotlinx.serialization.Serializable
data class Language(
    val languages: List<Language>
)