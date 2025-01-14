package com.unistra.m2info.composemymeal.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unistra.m2info.composemymeal.api.MealDetail
import java.io.File

object FileStorage {
    private const val FILE_NAME = "favorites.json"

    fun saveFavorites(context: Context, favorites: List<MealDetail>) {
        val completeFavorites = favorites.filter { it.isComplete() }
        val jsonString = Gson().toJson(completeFavorites)
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(jsonString)
    }

    fun MealDetail.isComplete(): Boolean {
        return !idMeal.isNullOrEmpty() && !strMeal.isNullOrEmpty() && !strMealThumb.isNullOrEmpty() && !strInstructions.isNullOrEmpty()
    }
    
    fun loadFavorites(context: Context): List<MealDetail> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()
        val jsonString = file.readText()
        val gson = Gson()
        val type = object : TypeToken<List<MealDetail>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
