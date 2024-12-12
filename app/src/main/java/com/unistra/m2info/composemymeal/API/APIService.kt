package com.unistra.m2info.composemymeal.API

import com.unistra.m2info.composemymeal.models.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/json/v1/1/lookup.php?i=52772")
    fun getMealByID(): Call<Meal>
}
