package com.unistra.m2info.composemymeal

import androidx.compose.runtime.mutableStateListOf

object FavoritesManager {
    private val _favorites = mutableStateListOf<MealDetail>() // Reactive favorites list
    val favorites: List<MealDetail> get() = _favorites

    fun addFavorite(meal: MealDetail) {
        if (_favorites.none { it.idMeal == meal.idMeal }) {
            _favorites.add(meal)
        }
    }

    fun removeFavorite(meal: MealDetail) {
        _favorites.removeAll { it.idMeal == meal.idMeal }
    }

    fun toggleFavorite(meal: MealDetail) {
        if (_favorites.any { it.idMeal == meal.idMeal }) {
            removeFavorite(meal)
        } else {
            addFavorite(meal)
        }
    }

    fun isFavorite(meal: MealDetail): Boolean {
        return _favorites.any { it.idMeal == meal.idMeal }
    }
}
