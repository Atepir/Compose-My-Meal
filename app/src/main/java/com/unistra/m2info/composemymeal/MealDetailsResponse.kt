package com.unistra.m2info.composemymeal

data class MealDetailsResponse(
    val meals: List<MealDetail>
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strTags: String?,
    val strYoutube: String?
)
