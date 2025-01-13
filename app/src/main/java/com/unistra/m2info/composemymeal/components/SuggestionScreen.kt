package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.material3.* // For MaterialTheme, Text, Button, and other Material 3 components
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

import com.unistra.m2info.composemymeal.viewmodels.SuggestionViewModel


@Composable
fun SuggestionScreen(navController: NavController, sheetStack: SheetStack) {
    val viewModel = remember { SuggestionViewModel() }
    var showShareDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val randomMeal by viewModel.randomMeal
    val isLoading by viewModel.isLoading

    var handled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchRandomMeal()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(top = 50.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { handled = false }, // Reset the flag when the drag ends
                    onHorizontalDrag = { _, dragAmount ->
                        if (!handled) {
                            when {
                                dragAmount < -50 -> {
                                    navController.navigate("favorites")
                                    handled = true
                                }
                            }
                        }
                    })
            }
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                randomMeal?.let { meal ->
                    MealDetailScreen(mealId = meal.idMeal)
                } ?: Text("No meal data available.")
            }
        }
    }
}

