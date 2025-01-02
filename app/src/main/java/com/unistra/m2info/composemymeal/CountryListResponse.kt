package com.unistra.m2info.composemymeal

data class CountryListResponse(
    val meals: List<Country>
)

data class Country(
    val strArea: String
)
