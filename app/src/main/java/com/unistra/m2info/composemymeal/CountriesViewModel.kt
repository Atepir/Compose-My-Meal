package com.unistra.m2info.composemymeal

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesViewModel : ViewModel() {
    val countries = mutableStateOf<List<String>>(emptyList())
    val meals = mutableStateOf<List<MealDetail>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun fetchCountries() {
        RetrofitInstance.api.getCountries().enqueue(object : Callback<CountryListResponse> {
            override fun onResponse(
                call: Call<CountryListResponse>,
                response: Response<CountryListResponse>
            ) {
                if (response.isSuccessful) {
                    val countriesList = response.body()?.meals?.map { it.strArea }
                    countries.value = countriesList ?: emptyList()
                    println("Countries loaded: $countriesList")
                } else {
                    println("API response unsuccessful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CountryListResponse>, t: Throwable) {
                println("API call failed: ${t.localizedMessage}")
            }
        })
    }

    fun fetchMealsByCountry(country: String) {
        isLoading.value = true
        RetrofitInstance.api.getMealsByCountry(country).enqueue(object : Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                isLoading.value = false
                meals.value = response.body()?.meals ?: emptyList()
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                isLoading.value = false
                println("Failed to fetch meals: ${t.localizedMessage}")
            }
        })
    }
}
