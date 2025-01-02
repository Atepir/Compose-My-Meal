package com.unistra.m2info.composemymeal

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object FileStorage {
    private const val FILE_NAME = "favorites.json"

    fun saveFavorites(context: Context, favorites: List<MealDetail>) {
        val gson = Gson()
        val jsonString = gson.toJson(favorites)
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(jsonString)
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
