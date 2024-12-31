package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
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

    LaunchedEffect(Unit) {
        viewModel.fetchRandomMeal()
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    .fillMaxSize()
                    .padding(bottom = 64.dp), // Leave space for BottomNavigation
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                randomMeal?.let { meal ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()) // Make content scrollable
                            .padding(16.dp)
                    ) {
                        Text(
                            text = meal.strMeal,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = "Meal Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(bottom = 8.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            IconButton(
                                onClick = {
                                    randomMeal?.let { FavoritesManager.toggleFavorite(it) }
                                },
                                modifier = Modifier.size(40.dp)
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

                            Spacer(modifier = Modifier.width(16.dp))

                            IconButton(
                                onClick = { showShareDialog = true },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.share),
                                    contentDescription = "Share",
                                )
                            }
                        }

                        // Display ingredients with measures
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
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
                                    Text(
                                        text = "• $ingredient - $measure",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        // Display instructions
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = meal.strInstructions,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    if (showShareDialog) {
                        ShareDialog(context = context, meal = meal, onDismiss = { showShareDialog = false })
                    }
                    
                } ?: Text("No meal data available.")
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            BottomNavigation(sheetStack = sheetStack)
        }
    }
}

