package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.FavoritesManager
import com.unistra.m2info.composemymeal.MealDetail
import com.unistra.m2info.composemymeal.R

@Composable
fun MealCard(meal: MealDetail, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = meal.strMeal,
                    modifier = Modifier
                        .width(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = meal.strMeal.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    meal.strTags?.let {
                        Text(
                            text = it.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                    meal.strCategory?.let {
                        Text(
                            text = it.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(8.dp)) {
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
            }
        }
    }
}
