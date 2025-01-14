package com.unistra.m2info.composemymeal.viewmodels

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance

class MealDetailViewModel : ViewModel() {

    val mealDetail = mutableStateOf<MealDetail?>(null)
    val isLoading = mutableStateOf(false)

    private val mealCache = mutableMapOf<String, MealDetail>()

    fun fetchMealDetails(mealId: String, forceRefresh: Boolean = false) {
        if (!forceRefresh && mealCache.containsKey(mealId)) {
            mealDetail.value = mealCache[mealId]
            return
        }

        isLoading.value = true
        RetrofitInstance.api.getMealDetails(mealId).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.firstOrNull()
                    mealDetail.value = meal
                    if (meal != null) {
                        mealCache[mealId] = meal
                    }
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
            }
        })
    }
}