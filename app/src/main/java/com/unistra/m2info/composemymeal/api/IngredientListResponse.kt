package com.unistra.m2info.composemymeal.api

data class IngredientListResponse(
    val meals: List<Ingredient>
)

data class Ingredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?
)

