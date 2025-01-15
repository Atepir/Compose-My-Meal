package com.unistra.m2info.composemymeal.layout

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unistra.m2info.composemymeal.ui.theme.ComposeMyMealTheme
import com.unistra.m2info.composemymeal.ui.theme.UbuntuFontFamily
import kotlinx.coroutines.selects.select

@Composable
fun CustomRow(
    items: List<String>,
    selectedIndex: Int,
    onTabSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        // Set the indicator to null to remove the underline
        indicator = {},
        divider = {},
        containerColor = Color.Transparent,
        edgePadding = 0.dp,
    ) {
        items.forEachIndexed { index, title ->

            val tabModifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    indication = null, // No ripple effect
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onTabSelected(index)
                }

            Text(
                text = title,
                modifier = tabModifier,
                fontWeight = FontWeight.Bold,
                fontFamily = UbuntuFontFamily,
                fontSize = 22.sp,
                color = if (isSystemInDarkTheme()) {
                    if (index == selectedIndex) Color.White else Color.Gray
                } else {
                    if (index == selectedIndex) Color.Black else Color.LightGray
                }
            )
        }
    }
}
