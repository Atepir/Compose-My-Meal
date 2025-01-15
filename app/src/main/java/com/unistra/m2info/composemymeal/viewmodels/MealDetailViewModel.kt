package com.unistra.m2info.composemymeal.viewmodels

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import com.unistra.m2info.composemymeal.utils.FavoritesManager

class MealDetailViewModel : ViewModel() {

    val mealDetail = mutableStateOf<MealDetail?>(null)
    val isLoading = mutableStateOf(true)
    val errorMessage = mutableStateOf<String?>(null)

    private val mealCache = mutableMapOf<String, MealDetail>()

    fun fetchMealDetails(mealId: String, forceRefresh: Boolean = false) {
        isLoading.value = true

        val localMeal = FavoritesManager.favorites.find { it.idMeal == mealId }
        if (localMeal != null && !forceRefresh) {
            mealDetail.value = localMeal
            isLoading.value = false
            return
        }

        errorMessage.value = null

        RetrofitInstance.api.getMealDetails(mealId).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (meal != null) {
                        mealDetail.value = meal
                        mealCache[mealId] = meal
                    } else {
                        errorMessage.value = "Meal details not available."
                    }
                } else {
                    errorMessage.value = "Failed to load meal details: ${response.message()}"
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                errorMessage.value = "Unable to load meal details. Please try again."
                isLoading.value = false
            }
        })
    }

}