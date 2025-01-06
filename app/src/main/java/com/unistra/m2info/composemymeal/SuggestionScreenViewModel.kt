package com.unistra.m2info.composemymeal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf

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

