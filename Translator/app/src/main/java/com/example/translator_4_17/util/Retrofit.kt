package com.example.translator_4_17.util

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object Retrofit {
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> createApi(engine: TranslationEngine): T {
        val baseUrl = engine.getUrl()
        val httpClient = OkHttpClient.Builder()
        val contentType = "application/json".toMediaTypeOrNull()!!

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.json.asConverterFactory(contentType))
            .client(httpClient.build())
            .build()
            .create(T::class.java)
    }
}
