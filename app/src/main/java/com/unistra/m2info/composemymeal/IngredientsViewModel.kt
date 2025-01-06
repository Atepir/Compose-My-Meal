package com.unistra.m2info.composemymeal

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientsViewModel : ViewModel() {

    val meals = mutableStateOf<List<MealDetail>>(emptyList())
    val isLoading = mutableStateOf(false)
    val ingredients = mutableStateOf<List<String>>(emptyList())

    fun fetchMealsByIngredient(ingredient: String) {
        isLoading.value = true

        RetrofitInstance.api.getMealsByIngredient(ingredient).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    meals.value = response.body()?.meals ?: emptyList()
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun fetchIngredients() {
        RetrofitInstance.api.getIngredients().enqueue(object : Callback<IngredientListResponse> {
            override fun onResponse(
                call: Call<IngredientListResponse>,
                response: Response<IngredientListResponse>
            ) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()?.meals?.map { it.strIngredient }
                    ingredients.value = ingredientsList ?: emptyList()
                    println("Ingredients loaded: $ingredientsList")
                } else {
                    println("API response unsuccessful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<IngredientListResponse>, t: Throwable) {
                println("API call failed: ${t.localizedMessage}")
            }
        })
    }

}

