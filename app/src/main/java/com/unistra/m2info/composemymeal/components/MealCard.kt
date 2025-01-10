package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.MealDetail

@Composable
fun MealCard(meal: MealDetail, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Left
            )
        }
    }
}
