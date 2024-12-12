package com.unistra.m2info.composemymeal.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val key = "1"
    private const val version = "v1"
    private const val url = "https://www.themealdb.com/"

    private const val BASE_URL = url + "api/json/$version/$key/lookup.php?i=52772"

    fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}