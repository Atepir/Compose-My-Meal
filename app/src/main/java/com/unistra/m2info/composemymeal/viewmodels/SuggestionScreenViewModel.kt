package com.unistra.m2info.composemymeal.viewmodels

import androidx.compose.runtime.mutableStateListOf
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
    private val _favorites = mutableStateListOf<MealDetail>() // Liked meals list

    fun fetchRandomMeal() {
        isLoading.value = true
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(
                call: Call<MealDetailsResponse>,
                response: Response<MealDetailsResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    randomMeal.value = response.body()?.meals?.firstOrNull()
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
                // Handle error
            }
        })
    }
}

