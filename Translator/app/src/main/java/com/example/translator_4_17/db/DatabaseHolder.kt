package com.example.translator_4_17.db

import android.content.Context
import androidx.room.Room

class DatabaseHolder {

    fun initDb(applicationContext: Context) {
        Db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "Database"
        ).build()
    }

    companion object {
        lateinit var Db: AppDatabase
    }
}