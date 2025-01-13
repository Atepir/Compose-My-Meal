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

    fun fetchMealsByIngredient(ingredient: String) {
        isLoading.value = true

        RetrofitInstance.api.getMealsByIngredient(ingredient).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    meals.value = response.body()?.meals ?: emptyList()
                    filteredMeals.value = meals.value
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

    fun fetchIngredients() {
        RetrofitInstance.api.getIngredients().enqueue(object : Callback<IngredientListResponse> {
            override fun onResponse(call: Call<IngredientListResponse>, response: Response<IngredientListResponse>) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()?.meals?.map { it.strIngredient }
                    ingredients.value = ingredientsList ?: emptyList()
                }
            }

            override fun onFailure(call: Call<IngredientListResponse>, t: Throwable) {}
        })
    }
}


