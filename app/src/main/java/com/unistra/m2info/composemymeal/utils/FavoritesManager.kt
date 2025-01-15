package com.unistra.m2info.composemymeal.utils

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            RetrofitInstance.api.getMealDetails(meal.idMeal).enqueue(object : Callback<MealDetailsResponse> {
                override fun onResponse(
                    call: Call<MealDetailsResponse>,
                    response: Response<MealDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val mealToAdd = response.body()?.meals?.firstOrNull()
                        if (mealToAdd != null) {
                            _favorites.add(mealToAdd)
                            saveFavorites(context)
                        } else {
                            println("Meal details not found")
                        }
                    } else {
                        println("Failed to fetch meal details: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
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

    fun addFavoriteById(context: Context, mealId: String) {
        RetrofitInstance.api.getMealDetails(mealId).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        addFavorite(context, meal)

                    } else {
                        println("hh")
                    }
                } else {
                    println("hh")
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                println("hh")
            }
        })
    }

    private fun saveFavorites(context: Context) {
        FileStorage.saveFavorites(context, _favorites)
    }
}

