package com.unistra.m2info.composemymeal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SuggestionScreen() {
    // UI based on the provided wireframe
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "A very nice recipe!", style = MaterialTheme.typography.h5)

        // Recipe details (image, flag, ingredients, description)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace this with Image composable for your image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Image Placeholder")
            }

            Text(text = "Ingredients", style = MaterialTheme.typography.subtitle1)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(3) { Text(text = "🍅 Tomato 1kg") }
            }

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Gravida laoreet ut purus dui class ultricies.",
                style = MaterialTheme.typography.body2
            )
        }

        // Buttons at the bottom
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { /* Browse action */ }) {
                Text("Browse")
            }
            Button(onClick = { /* Surprise Me action */ }) {
                Text("Surprise Me")
            }
        }
    }
}