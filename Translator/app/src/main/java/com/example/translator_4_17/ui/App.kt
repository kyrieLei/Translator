package com.example.translator_4_17.ui

import android.app.Application
import com.example.translator_4_17.db.DatabaseHolder
import com.example.translator_4_17.util.Preferences
import com.example.translator_4_17.util.tts
import com.huawei.hms.mlsdk.common.MLApplication

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MLApplication.initialize(applicationContext);// 多进程调用。

        MLApplication.getInstance().apiKey =
            "DAEDAB+Ft0j1I/qq1Bj5hf4jlMWEpyY1VQHVF/Or8Mh2anuvNhaddtWdg3dsZ6jycYPLzdQmLa4+9vy+dufDOv96Tzae/o5G2bu5oQ=="
        MLApplication.getInstance().setUserRegion(MLApplication.REGION_DR_CHINA)
        DatabaseHolder().initDb(this)
        Preferences.initialize(this)
        tts.initTTS(this)
    }
}