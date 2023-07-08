package com.example.translator_4_17.util

import com.example.translator_4_17.db.data.Language
import com.example.translator_4_17.db.data.Translation
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


abstract class TranslationEngine(
    val name: String,
    val defaultUrl: String,
    val autoLanguageCode: String,
) {
    abstract fun create(): TranslationEngine

    abstract suspend fun getLanguages(): List<Language>

    abstract suspend fun translate(query: String, source: String, target: String): Translation

    private val urlPrefKey = this.name + Preferences.instanceUrlKey

    fun sourceOrAuto(source: String) = if (source == "") autoLanguageCode else source

    open fun getUrl() = Preferences.get(
        urlPrefKey,
        this.defaultUrl
    ).toHttpUrlOrNull()?.toString() ?: defaultUrl
}
