package com.unistra.m2info.composemymeal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("random.php")
    fun getRandomMeal(): Call<MealDetailsResponse>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") mealId: String): Call<MealDetailsResponse>
}