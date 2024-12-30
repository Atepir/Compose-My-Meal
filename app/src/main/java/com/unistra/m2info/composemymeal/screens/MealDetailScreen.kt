package com.unistra.m2info.composemymeal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.FavoritesManager
import com.unistra.m2info.composemymeal.MealDetailViewModel
import com.unistra.m2info.composemymeal.R


@Composable
fun MealDetailScreen(mealId: String, viewModel: MealDetailViewModel = viewModel()) {

    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    val meal = viewModel.mealDetail.value
    val isLoading = viewModel.isLoading.value

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        meal?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
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

                AsyncImage(
                    model = it.strMealThumb,
                    contentDescription = "Meal Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            meal?.let { FavoritesManager.toggleFavorite(it) }
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (meal?.let { FavoritesManager.isFavorite(it) } == true)
                                    R.drawable.heart_red
                                else
                                    R.drawable.heart
                            ),
                            contentDescription = "Like",
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = {}, modifier = Modifier.size(40.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "Share",
                        )
                    }
                }

                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                it.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
                    Text(
                        text = "• $ingredient - $measure",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = it.strInstructions,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Start
                )
            }
        } ?: Text(
            text = "Meal details not available.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
