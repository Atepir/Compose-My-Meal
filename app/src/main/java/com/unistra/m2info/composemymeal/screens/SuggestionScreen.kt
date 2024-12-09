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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unistra.m2info.composemymeal.layout.SheetStack
import androidx.compose.foundation.layout.* // For Column, Row, Box, and layout modifiers
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.* // For MaterialTheme, Text, Button, and other Material 3 components
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.layout.BottomNavigation

import com.unistra.m2info.composemymeal.R

@Composable
fun SuggestionScreen(navController: NavController, sheetStack: SheetStack) {
    var isLiked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "A very nice recipe!",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.orange),
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(bottom = 8.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.france),
                        contentDescription = "French Flag",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { isLiked = !isLiked },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isLiked) R.drawable.heart_red else R.drawable.heart
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
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) {
                    ClickableText(
                        text = AnnotatedString("🍅 Tomato 1kg"),
                        onClick = {},
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Gravida laoreet ut purus dui class ultricies! Lorem ipsum dolor sit amet, consectetur adipiscing elit" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.navigate("browse") }) {
                Text("Browse")
            }
            Button(onClick = { /* Surprise Me functionality */ }) {
                Text("Surprise Me")
            }
        }
        BottomNavigation(sheetStack)
    }
}
