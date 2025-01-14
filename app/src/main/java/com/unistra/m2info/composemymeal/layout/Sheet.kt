package com.unistra.m2info.composemymeal.layout

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val coroutineScope = rememberCoroutineScope()

    var sheetStackShown = remember { mutableStateOf(false)}

    // Listen for changes to the sheet state
    LaunchedEffect(sheetState.isVisible) {
        if (!sheetStackShown.value) {
            sheetState.show()
            sheetStackShown.value = true
        }
        if (!sheetState.isVisible) {
            onDismissRequest() // Trigger dismiss when the sheet is
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            // Content of the sheet
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .width(72.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSystemInDarkTheme()) Color.White else Color.Gray)
            )
            content()
        },
        sheetElevation = 10.dp,
        sheetShape = RoundedCornerShape(24.dp),
        sheetGesturesEnabled = true,
        modifier = Modifier.padding(top = 16.dp),
        sheetBackgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
    ) {
        // Optionally, main screen content goes here
    }
}
