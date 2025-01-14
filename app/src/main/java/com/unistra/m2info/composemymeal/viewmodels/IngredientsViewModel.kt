package com.unistra.m2info.composemymeal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.IngredientListResponse
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientsViewModel : ViewModel() {

    val meals = mutableStateOf<List<MealDetail>>(emptyList())
    val filteredMeals = mutableStateOf<List<MealDetail>>(emptyList())
    val isLoading = mutableStateOf(false)
    val ingredients = mutableStateOf<List<String>>(emptyList())

    // Caches
    private val ingredientsCache = mutableListOf<String>()
    private val mealsCache = mutableMapOf<String, List<MealDetail>>()

    fun fetchMealsByIngredient(ingredient: String, forceRefresh: Boolean = false) {
        if (!forceRefresh && mealsCache.containsKey(ingredient)) {
            meals.value = mealsCache[ingredient]!!
            filteredMeals.value = meals.value
            return
        }

        isLoading.value = true

        RetrofitInstance.api.getMealsByIngredient(ingredient).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val fetchedMeals = response.body()?.meals ?: emptyList()
                    meals.value = fetchedMeals
                    filteredMeals.value = fetchedMeals
                    mealsCache[ingredient] = fetchedMeals
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun fetchMealsBySearchQuery(query: String) {
        filteredMeals.value = if (query.isBlank()) {
            meals.value
        } else {
            meals.value.filter { it.strMeal.contains(query, ignoreCase = true) }
        }
    }

    fun fetchIngredients(forceRefresh: Boolean = false) {
        if (!forceRefresh && ingredientsCache.isNotEmpty()) {
            ingredients.value = ingredientsCache
            return
        }

        RetrofitInstance.api.getIngredients().enqueue(object : Callback<IngredientListResponse> {
            override fun onResponse(call: Call<IngredientListResponse>, response: Response<IngredientListResponse>) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()?.meals?.map { it.strIngredient }
                    ingredients.value = ingredientsList ?: emptyList()
                    ingredientsCache.clear()
                    ingredientsCache.addAll(ingredients.value)
                }
            }

            override fun onFailure(call: Call<IngredientListResponse>, t: Throwable) {}
        })
    }
}