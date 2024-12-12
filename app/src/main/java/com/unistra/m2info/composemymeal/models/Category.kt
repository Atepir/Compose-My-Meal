package com.unistra.m2info.composemymeal.models
import kotlinx.serialization.Serializable


@Serializable
data class CategoryResponse(
    val categories: List<Category>
)

@Serializable
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)