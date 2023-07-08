package com.example.translator_4_17.api.data

@kotlinx.serialization.Serializable
data class Definition(
    val list: List<OtherInfo> = listOf(),
    val type: String? = null
)