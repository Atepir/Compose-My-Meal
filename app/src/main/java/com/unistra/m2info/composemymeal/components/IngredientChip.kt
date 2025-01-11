package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

@Composable
fun IngredientChip(ingredient: String, measure: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(
                color = Color(0x10000000),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(0.7f).height(24.dp),
        ) {
            val imageUrl = "https://www.themealdb.com/images/ingredients/${ingredient}-Small.png"
            AsyncImage(
                model = imageUrl,
                contentDescription = ingredient,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = ingredient.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = measure.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
    }
}