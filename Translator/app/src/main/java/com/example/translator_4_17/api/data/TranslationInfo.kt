package com.example.translator_4_17.api.data

import kotlinx.serialization.Serializable

@Serializable
data class TranslationInfo(
    val definitions: List<Definition> = listOf(),
    val examples: List<String> = listOf(),
    val detectedSource: String? = null
)
