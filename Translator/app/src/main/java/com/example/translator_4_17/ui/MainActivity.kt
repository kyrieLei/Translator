package com.example.translator_4_17.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.ui.navigation.NavigationHost
import com.example.translator_4_17.ui.theme.TranslatorTheme
import com.example.translator_4_17.util.Preferences
import com.example.translator_4_17.util.hexToColor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private lateinit var mainModel: TranslationModel
    var themeMode by mutableStateOf(
        Preferences.getThemeMode()
    )

    var accentColor by mutableStateOf(Preferences.getAccentColor())
    override fun onCreate(savedInstanceState: Bundle?) {


        mainModel = ViewModelProvider(this)[TranslationModel::class.java]
        super.onCreate(savedInstanceState)


        setContent {
            TranslatorTheme(themeMode, accentColor?.hexToColor()) {
                val navController = rememberNavController()
                NavigationHost(navController, mainModel)
            }
        }

        handleIntentData()
    }

    override fun onStop() {
        Preferences.put(
            Preferences.sourceLanguage,
            Json.encodeToString(mainModel.sourceLanguage)
        )
        Preferences.put(
            Preferences.targetLanguage,
            Json.encodeToString(mainModel.targetLanguage)
        )
        super.onStop()
    }

    @SuppressLint("InlinedApi")
    private fun getIntentText(): String? {
        return intent.getCharSequenceExtra(Intent.EXTRA_TEXT)?.toString()
            ?: intent.takeIf { true }
                ?.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
            ?: intent.getCharSequenceExtra(Intent.ACTION_SEND)?.toString()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        handleIntentData()
    }

    private fun handleIntentData() {
        getIntentText()?.let {
            mainModel.insertedText = it
            mainModel.translateNow()
        }

    }

}
