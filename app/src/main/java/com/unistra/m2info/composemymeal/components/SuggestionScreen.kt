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
            .padding(top = 56.dp)
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
                    .background(Color.LightGray)
                    .padding(bottom = 88.dp), // Leave space for BottomNavigation
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()) // Make content scrollable
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp).padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = meal.strMeal,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )
                            Row(){
                                IconButton(
                                    onClick = {
                                        randomMeal?.let { FavoritesManager.toggleFavorite(context, it) }
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (randomMeal?.let { FavoritesManager.isFavorite(it) } == true)
                                                R.drawable.heart_red
                                            else
                                                R.drawable.heart
                                        ),
                                        contentDescription = "Like",
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                IconButton(
                                    onClick = { showShareDialog = true },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.share),
                                        contentDescription = "Share",
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = "Meal Image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }

                        // Display ingredients with measures
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        randomMeal?.let { meal ->
                            val ingredientsWithMeasures = meal.getIngredientsWithMeasures()
                            if (ingredientsWithMeasures.isEmpty()) {
                                Text(
                                    text = "No ingredients available.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            } else {
                                ingredientsWithMeasures.forEach { (ingredient, measure) ->
                                    if (ingredient != null && measure != null) {
                                        IngredientChip(ingredient, measure)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 12.dp))

                        // Display instructions
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Text(
                            text = meal.strInstructions,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                                .padding(bottom = 80.dp) // Space for bottom navigation
                        )
                    }
                    if (showShareDialog) {
                        ShareDialog(context = context, meal = meal, onDismiss = { showShareDialog = false })
                    }

                } ?: Text("No meal data available.")
            }
        }


    }
}

