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
import androidx.compose.ui.text.style.TextAlign

@Composable
fun IngredientChip(ingredient: String, measure: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ingredient.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = measure.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
        )
    }
}