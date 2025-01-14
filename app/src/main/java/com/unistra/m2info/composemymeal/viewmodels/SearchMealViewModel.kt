package com.unistra.m2info.composemymeal.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMealViewModel : ViewModel() {
    val filteredMeals = mutableStateOf<List<MealDetail>>(emptyList())
    val meals = mutableStateOf<List<MealDetail>>(emptyList())
    val isLoading = mutableStateOf(false)

    private var cachedMeals: List<MealDetail>? = null

    fun fetchAllMeals(forceRefresh: Boolean = false) {
        if (!forceRefresh && cachedMeals != null) {
            meals.value = cachedMeals!!
            filteredMeals.value = cachedMeals!!
            return
        }

        isLoading.value = true
        val allMeals = mutableListOf<MealDetail>()
        val letters = 'A'..'Z'

        var remainingCalls = letters.count()

        for (letter in letters) {
            RetrofitInstance.api.getMealsByFirstLetter(letter.toString()).enqueue(object :
                Callback<MealDetailsResponse> {
                override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.meals?.let { allMeals.addAll(it) }
                    }
                    checkIfDone()
                }

                override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                    checkIfDone()
                }

                private fun checkIfDone() {
                    remainingCalls -= 1
                    if (remainingCalls == 0) {
                        isLoading.value = false
                        val sortedMeals = allMeals.sortedBy { it.strMeal }
                        cachedMeals = sortedMeals
                        meals.value = sortedMeals
                        filteredMeals.value = sortedMeals
                    }
                }
            })
        }
    }
}