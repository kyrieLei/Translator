package com.example.translator_4_17.models


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translator_4_17.R
import com.example.translator_4_17.constant.TranslationEngines
import com.example.translator_4_17.db.DatabaseHolder.Companion.Db
import com.example.translator_4_17.db.data.HistoryItem
import com.example.translator_4_17.db.data.Language
import com.example.translator_4_17.db.data.Translation
import com.example.translator_4_17.util.Preferences
import com.example.translator_4_17.util.TranslationEngine
import com.example.translator_4_17.util.awaitQuery
import com.example.translator_4_17.util.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TranslationModel : ViewModel() {
    private var engine: TranslationEngine = getCurrentEngine()


    var availableLanguages: List<Language> by mutableStateOf(
        emptyList()
    )


    var sourceLanguage: Language by mutableStateOf(
        getLanguageByPrefKey(Preferences.sourceLanguage) ?: Language("", "自动")
    )

    var targetLanguage: Language by mutableStateOf(
        getLanguageByPrefKey(Preferences.targetLanguage) ?: Language("zh", "简体中文")
    )


    var insertedText: String by mutableStateOf(
        ""
    )

    var translation: Translation by mutableStateOf(
        Translation("")
    )

    var translatedTexts: MutableMap<String, Translation> =
        TranslationEngines.engines
            .associate { it.name to Translation("") }
            .toMutableMap()


    var bookmarkedLanguages by mutableStateOf(listOf<Language>())

    var translating by mutableStateOf(false)

    private fun getLanguageByPrefKey(key: String): Language? {
        return try {
            Json.decodeFromString<Language>(Preferences.get(key, ""))
        } catch (e: Exception) {
            null
        }
    }

    fun enqueueTranslation() {
        if (!Preferences.get(Preferences.translateAutomatically, true)) return

        val insertedTextTemp = insertedText
        Handler(
            Looper.getMainLooper()
        ).postDelayed(
            {
                if (insertedTextTemp == insertedText) translateNow()
            },
            Preferences.get(
                Preferences.fetchDelay,
                500f
            ).toLong()
        )
    }

    fun translateNow() {
        if (insertedText == "" || targetLanguage == sourceLanguage) {
            translation = Translation("")
            return
        }

        translating = true

        translatedTexts = TranslationEngines.engines
            .associate { it.name to Translation("") }
            .toMutableMap()

        CoroutineScope(Dispatchers.IO).launch {
            val translation = try {
                engine.translate(
                    insertedText,
                    sourceLanguage.code,
                    targetLanguage.code
                )
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
                return@launch
            }

            translating = false

            if (insertedText != "") {
                this@TranslationModel.translation = translation
                translatedTexts[engine.name] = translation
                saveToHistory()
            }
        }

    }


    private fun saveToHistory() {
        if (!Preferences.get(
                Preferences.historyEnabledKey,
                true
            )
        ) {
            return
        }
        query {
            Db.historyDao().insertAll(
                HistoryItem(
                    sourceLanguageCode = sourceLanguage.code,
                    sourceLanguageName = sourceLanguage.name,
                    targetLanguageCode = targetLanguage.code,
                    targetLanguageName = targetLanguage.name,
                    insertedText = insertedText,
                    translatedText = translation.translatedText
                )
            )
        }
    }

    fun clearTranslation() {
        insertedText = ""
        translation = Translation("")
    }

    private fun fetchLanguages(onError: (Exception) -> Unit = {}) {
        viewModelScope.launch {
            val languages = try {
                Log.e("engine", engine.name)
                engine.getLanguages()
            } catch (e: Exception) {
                Log.e("Fetching languages", e.toString())
                onError.invoke(e)
                return@launch
            }
            this@TranslationModel.availableLanguages = languages
        }
    }

    fun getCurrentEngine() = TranslationEngines.engines[
        Preferences.get(Preferences.apiTypeKey, 0)
    ]


    fun refresh(context: Context) {
        engine = getCurrentEngine()
        fetchLanguages {
            Toast.makeText(context, R.string.server_error, Toast.LENGTH_LONG).show()
        }
        fetchBookmarkedLanguages()
    }

    private fun fetchBookmarkedLanguages() {
        bookmarkedLanguages = awaitQuery {
            Db.languageBookmarksDao().getAll()
        }
    }
}
