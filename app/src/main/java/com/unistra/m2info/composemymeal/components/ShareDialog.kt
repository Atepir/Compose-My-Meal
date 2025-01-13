package com.unistra.m2info.composemymeal.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.utils.shareViaFacebook
import com.unistra.m2info.composemymeal.utils.shareViaSms

@Composable
fun ShareDialog(context: Context, meal: MealDetail, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        title = {
            Text(text = "Share Meal")
        },
        text = {
            Column {
                Text(text = "Choose a platform to share this meal:")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        shareViaFacebook(context, meal)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Share on Facebook")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        shareViaSms(context, meal)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Share via Text")
                }
            }
        }
    )
}
