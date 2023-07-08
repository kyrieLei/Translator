package com.example.translator_4_17.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.translator_4_17.R
import java.util.*

object tts {
    private lateinit var tts: TextToSpeech
    private var ttsAvailable = false

    fun initTTS(context: Context) {
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) {
                Log.e("TTS", "Initialization Failed")
            } else {
                ttsAvailable = true
            }
        }
    }

    fun speak(context: Context, text: String, language: String) {
        val result: Int = tts.setLanguage(
            Locale(language)
        )

        if (result == TextToSpeech.LANG_MISSING_DATA ||
            result == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            Toast.makeText(context, R.string.language_not_supported, Toast.LENGTH_SHORT).show()
            return
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
