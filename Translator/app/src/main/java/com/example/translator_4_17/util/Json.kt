package com.example.translator_4_17.util

import kotlinx.serialization.json.Json

object Json {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
}