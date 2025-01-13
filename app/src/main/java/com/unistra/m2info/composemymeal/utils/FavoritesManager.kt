package com.unistra.m2info.composemymeal.utils

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.unistra.m2info.composemymeal.api.MealDetail

object FavoritesManager {
    private val _favorites = mutableStateListOf<MealDetail>() // Reactive favorites list
    val favorites: List<MealDetail> get() = _favorites

    fun initialize(context: Context) {
        val loadedFavorites = FileStorage.loadFavorites(context)
        _favorites.clear()
        _favorites.addAll(loadedFavorites)
    }

    fun addFavorite(context: Context, meal: MealDetail) {
        if (_favorites.none { it.idMeal == meal.idMeal }) {
            _favorites.add(meal)
            saveFavorites(context)
        }
    }

    fun removeFavorite(context: Context, meal: MealDetail) {
        _favorites.removeAll { it.idMeal == meal.idMeal }
        saveFavorites(context)
    }

    fun toggleFavorite(context: Context, meal: MealDetail) {
        if (_favorites.any { it.idMeal == meal.idMeal }) {
            removeFavorite(context, meal)
        } else {
            addFavorite(context, meal)
        }
    }

    fun isFavorite(meal: MealDetail): Boolean {
        return _favorites.any { it.idMeal == meal.idMeal }
    }

    private fun saveFavorites(context: Context) {
        FileStorage.saveFavorites(context, _favorites)
    }
}
