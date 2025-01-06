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
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strIngredient21: String?,
    val strIngredient22: String?,
    val strIngredient23: String?,
    val strIngredient24: String?,
    val strIngredient25: String?,
    val strIngredient26: String?,
    val strIngredient27: String?,
    val strIngredient28: String?,
    val strIngredient29: String?,
    val strIngredient30: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,
    val strMeasure21: String?,
    val strMeasure22: String?,
    val strMeasure23: String?,
    val strMeasure24: String?,
    val strMeasure25: String?,
    val strMeasure26: String?,
    val strMeasure27: String?,
    val strMeasure28: String?,
    val strMeasure29: String?,
    val strMeasure30: String?
) {
    fun getIngredientsWithMeasures(): List<Pair<String?, String?>> {
        val ingredients = listOf(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
            strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
            strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20,
            strIngredient21, strIngredient22, strIngredient23, strIngredient24, strIngredient25,
            strIngredient26, strIngredient27, strIngredient28, strIngredient29, strIngredient30
        )

        val measures = listOf(
            strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
            strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
            strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
            strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20,
            strMeasure21, strMeasure22, strMeasure23, strMeasure24, strMeasure25,
            strMeasure26, strMeasure27, strMeasure28, strMeasure29, strMeasure30
        )

        // Pair ingredients with their measures, filtering out null or blank entries
        return ingredients.zip(measures).filter { (ingredient, measure) ->
            !ingredient.isNullOrBlank() && !measure.isNullOrBlank()
        }
    }

}


