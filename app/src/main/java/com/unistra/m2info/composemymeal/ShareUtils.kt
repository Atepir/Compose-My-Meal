package com.unistra.m2info.composemymeal.utils

import android.content.Context
import android.content.Intent
import com.unistra.m2info.composemymeal.MealDetail

fun shareViaFacebook(context: Context, meal: MealDetail) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "Check out this amazing meal: ${meal.strMeal}\n\n${meal.strInstructions}\n\nImage: ${meal.strMealThumb}"
        )
    }
    intent.setPackage("com.facebook.katana") // Specify Facebook app package
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback if Facebook is not installed
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this amazing meal: ${meal.strMeal}\n\n${meal.strInstructions}\n\nImage: ${meal.strMealThumb}"
            )
        }.also {
            context.startActivity(Intent.createChooser(it, "Share Meal"))
        }
    }
}


fun shareViaSms(context: Context, meal: MealDetail) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        type = "vnd.android-dir/mms-sms"
        putExtra(
            "sms_body",
            "Check out this amazing meal: ${meal.strMeal}\n\n${meal.strInstructions}\n\nImage: ${meal.strMealThumb}"
        )
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback if SMS app is not available
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this amazing meal: ${meal.strMeal}\n\n${meal.strInstructions}\n\nImage: ${meal.strMealThumb}"
            )
        }.also {
            context.startActivity(Intent.createChooser(it, "Share Meal"))
        }
    }
}
