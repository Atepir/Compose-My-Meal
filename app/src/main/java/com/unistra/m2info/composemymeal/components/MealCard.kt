package com.unistra.m2info.composemymeal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.unistra.m2info.composemymeal.utils.FavoritesManager
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.R
import com.unistra.m2info.composemymeal.ui.theme.UbuntuFontFamily
import com.unistra.m2info.composemymeal.utils.countryIconMap
import com.unistra.m2info.composemymeal.viewmodels.MealDetailViewModel

@Composable
fun MealCard(meal: MealDetail, onClick: () -> Unit, viewModel: MealDetailViewModel = viewModel()) {
    val context = LocalContext.current
    var showShareDialog by remember { mutableStateOf(false) }
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
            disabledContainerColor = Color.White),
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = meal.strMeal,
                        modifier = Modifier
                            .width(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
//                    val iconRes = countryIconMap[meal.strArea] ?: R.drawable.france
//                    Image(
//                        painter = painterResource(id = iconRes),
//                        contentDescription = meal.strArea,
//                        modifier = Modifier
//                            .size(20.dp)
//                            .align(Alignment.TopEnd)
//                            .offset(x = 16.dp, y = 8.dp)
//                    )
                }

                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = meal.strMeal.replaceFirstChar { it.uppercase() },
                        fontFamily = UbuntuFontFamily,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    meal.strCategory?.let {
                        Text(
                            text = it.replaceFirstChar { it.uppercase() },
                            fontFamily = UbuntuFontFamily,
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

        if (showShareDialog) {
            viewModel.fetchMealDetails(meal.idMeal)
            viewModel.mealDetail.value?.let { ShareDialog(context = context, meal = it, onDismiss = { showShareDialog = false }) }
        }
    }
}
