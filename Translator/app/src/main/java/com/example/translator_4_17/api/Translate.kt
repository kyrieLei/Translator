package com.example.translator_4_17.api


import com.example.translator_4_17.api.data.TranslationResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface Translate {

    @GET("api/v1/{source}/{target}/{query}")
    suspend fun translate(
        @Path("source") source: String,
        @Path("target") target: String,
        @Path("query") query: String,
    ): TranslationResponse
}
