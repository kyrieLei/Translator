package com.example.translator_4_17.util

import android.content.Context
import android.content.SharedPreferences
import com.example.translator_4_17.constant.ThemeMode


object Preferences {
    const val instanceUrlKey = "instanceUrl"
    const val apiTypeKey = "apiTypeKey"
    const val historyEnabledKey = "historyEnabledKey"
    const val translateAutomatically = "translateAutomatically"
    const val fetchDelay = "fetchDelay"
    const val showAdditionalInfo = "showAdditionalInfoKey"
    const val themeModeKey = "themeMode"
    const val accentColorKey = "accentColor"
    const val sourceLanguage = "sourceLanguage"
    const val targetLanguage = "targetLanguage"

    lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(
            "preferences",
            Context.MODE_PRIVATE
        )
    }

    fun <T> put(key: String, value: T) {
        when (value) {
            is Boolean -> prefs.edit().putBoolean(key, value).apply()
            is String -> prefs.edit().putString(key, value).apply()
            is Int -> prefs.edit().putInt(key, value).apply()
            is Float -> prefs.edit().putFloat(key, value).apply()
            is Long -> prefs.edit().putLong(key, value).apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defValue: T): T {
        return when (defValue) {
            is Boolean -> prefs.getBoolean(key, defValue) as T
            is Int -> (prefs.getInt(key, defValue)) as T
            is Long -> (prefs.getLong(key, defValue)) as T
            is Float -> (prefs.getFloat(key, defValue)) as T
            else -> (prefs.getString(key, defValue.toString()) ?: defValue) as T
        }
    }

    fun getThemeMode() = get(themeModeKey, ThemeMode.AUTO.toString()).toInt()

    fun getAccentColor() = prefs.getString(accentColorKey, null)
}
