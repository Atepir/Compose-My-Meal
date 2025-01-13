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

    fun fetchMealDetails(mealId: String) {
        isLoading.value = true

        RetrofitInstance.api.getMealDetails(mealId).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    mealDetail.value = response.body()?.meals?.firstOrNull()
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
                // Handle failure
            }
        })
    }
}
