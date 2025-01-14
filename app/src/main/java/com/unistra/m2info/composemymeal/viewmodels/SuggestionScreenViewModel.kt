package com.unistra.m2info.composemymeal.viewmodels

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance

class SuggestionViewModel : ViewModel() {
    val randomMeal = mutableStateOf<MealDetail?>(null)
    val isLoading = mutableStateOf(false)

    var cachedMeal: MealDetail? = null

    fun fetchRandomMeal(forceRefresh: Boolean = false) {
        if (!forceRefresh && cachedMeal != null) {
            randomMeal.value = cachedMeal
            return
        }

        isLoading.value = true
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    randomMeal.value = meal
                    cachedMeal = meal
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }
}

