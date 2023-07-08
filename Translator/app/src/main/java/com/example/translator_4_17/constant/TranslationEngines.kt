package com.example.translator_4_17.constant

import com.example.translator_4_17.api.Engine

object TranslationEngines {
    val engines = listOf(
        Engine()
    ).map {
        it.create()
    }
}
