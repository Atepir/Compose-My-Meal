package com.unistra.m2info.composemymeal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.CountryListResponse
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesViewModel : ViewModel() {

    val countries = mutableStateOf<List<String>>(emptyList())
    val meals = mutableStateOf<List<MealDetail>>(emptyList())
    val isLoading = mutableStateOf(false)

    // Caches
    private val countriesCache = mutableListOf<String>()
    private val mealsByCountryCache = mutableMapOf<String, List<MealDetail>>()

    fun fetchCountries(forceRefresh: Boolean = false) {
        if (!forceRefresh && countriesCache.isNotEmpty()) {
            countries.value = countriesCache
            return
        }

        RetrofitInstance.api.getCountries().enqueue(object : Callback<CountryListResponse> {
            override fun onResponse(
                call: Call<CountryListResponse>,
                response: Response<CountryListResponse>
            ) {
                if (response.isSuccessful) {
                    val countriesList = response.body()?.meals?.map { it.strArea }
                    countries.value = countriesList ?: emptyList()
                    countriesCache.clear()
                    countriesCache.addAll(countries.value)
                } else {
                    println("API response unsuccessful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CountryListResponse>, t: Throwable) {
                println("API call failed: ${t.localizedMessage}")
            }
        })
    }

    fun fetchMealsByCountry(country: String, forceRefresh: Boolean = false) {
        if (!forceRefresh && mealsByCountryCache.containsKey(country)) {
            meals.value = mealsByCountryCache[country]!!
            return
        }

        isLoading.value = true
        if (country != "Unknown") {
            RetrofitInstance.api.getMealsByCountry(country).enqueue(object : Callback<MealDetailsResponse> {
                override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                    isLoading.value = false
                    val fetchedMeals = response.body()?.meals ?: emptyList()
                    meals.value = fetchedMeals
                    mealsByCountryCache[country] = fetchedMeals
                }

                override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                    isLoading.value = false
                    println("Failed to fetch meals: ${t.localizedMessage}")
                }
            })
        }
    }
}

