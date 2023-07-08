package com.example.translator_4_17.api.data

import kotlinx.serialization.Serializable

@Serializable
data class TranslationResponse(
    val info: TranslationInfo? = null,
    val translation: String = ""
)
