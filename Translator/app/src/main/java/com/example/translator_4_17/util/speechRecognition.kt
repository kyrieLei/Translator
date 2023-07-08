package com.example.translator_4_17.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.core.app.ActivityCompat
import com.huawei.hms.mlplugin.asr.MLAsrCaptureActivity
import com.huawei.hms.mlsdk.asr.MLAsrConstants
import com.huawei.hms.mlsdk.asr.MLAsrListener
import com.huawei.hms.mlsdk.asr.MLAsrRecognizer

object speechRecognition {
    private lateinit var tts: TextToSpeech
    private var ttsAvailable = false
    private val TAG = "SpeechToTextHelper"
    lateinit var mSpeechRecognizer: MLAsrRecognizer
    private var recognizing: ((Boolean, String?, String?) -> Unit)? = null
    fun checkPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
    }

    //Speak 部分
    fun recognizeSpeech(activity: Activity, onResult: (String) -> Unit) {
        // context为应用上下文信息。
        // 通过intent进行识别设置。

        this.recognizing = recognizing
        mSpeechRecognizer = MLAsrRecognizer.createAsrRecognizer(activity)
        mSpeechRecognizer.setAsrListener(object : MLAsrListener {
            private val RESULT_RECOGNIZING_KEY = "results_recognizing"

            //获取声音输出结果KEY
            private val RESULT_RECOGNIZED_KEY = "results_recognized"
            override fun onStartListening() {
            }

            override fun onStartingOfSpeech() {
            }

            override fun onVoiceDataReceived(data: ByteArray, energy: Float, bundle: Bundle) {
            }

            override fun onRecognizingResults(partialResults: Bundle) {
                // 从MLAsrRecognizer接收到持续语音识别的文本，该接口并非运行在主线程中，返回结果需要在子线程中处理。

                val data = partialResults?.getString(RESULT_RECOGNIZING_KEY).toString()
                data.let(onResult)
            }

            override fun onResults(results: Bundle) {
                // 语音识别的文本数据，该接口并非运行在主线程中，返回结果需要在子线程中处理。
                val data = results?.getString(RESULT_RECOGNIZED_KEY).toString()
                data.let(onResult)
            }

            override fun onError(error: Int, errorMessage: String) {
            }

            override fun onState(state: Int, params: Bundle) {
            }
        })
        val intent = Intent(activity, MLAsrCaptureActivity::class.java)
            // 设置识别语言为英语，若不设置，则默认识别英语。支持设置："zh-CN":中文；"en-US":英语；"fr-FR":法语；"es-ES":西班牙语；"de-DE":德语；"it-IT":意大利语；"ar": 阿拉伯语；"ru-RU":俄语；“th_TH”：泰语；“ms_MY”：马来语；“fil_PH”：菲律宾语；"tr-TR"：土耳其语。
            .putExtra(MLAsrConstants.LANGUAGE, "zh-CN")
            // 设置识别文本返回模式为边识别边出字，若不设置，默认为边识别边出字。支持设置：
            .putExtra(MLAsrConstants.FEATURE, MLAsrConstants.FEATURE_WORDFLUX)
            // 设置使用场景，MLAsrConstants.SCENES_SHOPPING：表示购物，仅支持中文，该场景对华为商品名识别进行了优化。
            .putExtra(MLAsrConstants.SCENES, MLAsrConstants.SCENES_SHOPPING)
            .putExtra(MLAsrConstants.VAD_START_MUTE_DURATION, 6000)
            .putExtra(MLAsrConstants.VAD_END_MUTE_DURATION, 700)
            .putExtra(MLAsrConstants.PUNCTUATION_ENABLE, true)
        mSpeechRecognizer.startRecognizing(intent)
        activity.startActivity(intent)

    }
}