package com.unistra.m2info.composemymeal.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unistra.m2info.composemymeal.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Sheet(
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    val coroutineScope = rememberCoroutineScope()

    // Listen for changes to the sheet state
    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) {
            onDismissRequest() // Trigger dismiss when the sheet is hidden
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            // Content of the sheet
            content()
        },
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(16.dp),
        sheetGesturesEnabled = true,
        modifier = Modifier.padding(top = 16.dp),
        sheetBackgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    ) {
        // Optionally, main screen content goes here
    }
}
