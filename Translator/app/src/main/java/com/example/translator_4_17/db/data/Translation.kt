package com.example.translator_4_17.db.data

data class Translation(
    val translatedText: String,
    val detectedLanguage: String? = null,
    val transliterations: List<String>? = null,
    val definitions: List<Definition>? = null,
    val examples: List<String>? = null
)

