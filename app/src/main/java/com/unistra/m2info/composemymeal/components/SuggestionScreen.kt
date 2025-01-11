package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.material3.* // For MaterialTheme, Text, Button, and other Material 3 components
import androidx.compose.ui.graphics.Color
import com.unistra.m2info.composemymeal.layout.BottomNavigation
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.FavoritesManager
import com.unistra.m2info.composemymeal.R
import com.unistra.m2info.composemymeal.ShareDialog

import com.unistra.m2info.composemymeal.SuggestionViewModel


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

