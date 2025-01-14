package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.utils.FavoritesManager
import com.unistra.m2info.composemymeal.viewmodels.MealDetailViewModel
import com.unistra.m2info.composemymeal.R
import com.unistra.m2info.composemymeal.utils.countryIconMap
import com.unistra.m2info.composemymeal.layout.SheetStack
import com.unistra.m2info.composemymeal.ui.theme.UbuntuFontFamily


@Composable
fun MealDetailScreen(mealId: String, sheetStack: SheetStack? = null, viewModel: MealDetailViewModel = viewModel()) {
    val context = LocalContext.current
    var showShareDialog by remember { mutableStateOf(false) }

    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    val meal = viewModel.mealDetail.value
    val isLoading = viewModel.isLoading.value

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
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
                    .background(Color.Transparent)
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
                            fontFamily = UbuntuFontFamily
                        ),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Row(){
                        IconButton(
                            onClick = {
                                meal?.let { FavoritesManager.toggleFavorite(context, it) }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = (if (meal?.let { FavoritesManager.isFavorite(it) } == true)
                                                    Icons.Default.Favorite
                                                else Icons.Default.FavoriteBorder) as ImageVector,
                                contentDescription = "Like",
                                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = { showShareDialog = true },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // Recipe image
                    AsyncImage(
                        model = it.strMealThumb,
                        contentDescription = "Meal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    if (it.strArea != "Unknown") {
                        // Country icon
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.9f))
                                .align(Alignment.TopStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val iconRes = countryIconMap[it.strArea] ?: R.drawable.france
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = it.strArea,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(4.dp)
                            )
                            it.strArea?.let { country ->
                                Text(
                                    text = country,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                it.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
                    if (ingredient != null && measure != null) {
                        IngredientChip(ingredient, measure)
                    }
                }

                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Text(
                    text = it.strInstructions,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp).padding(bottom = 100.dp), // Extra space at bottom for navigation
                    textAlign = TextAlign.Start
                )
            }

            if (showShareDialog) {
                ShareDialog(context = context, meal = it, onDismiss = { showShareDialog = false })
            }
        } ?: Text(
            text = "Meal details not available.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

