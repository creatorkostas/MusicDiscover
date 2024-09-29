package com.musicdiscover.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailPasswordLoginForm(
    //content: @Composable() () -> Unit
) {
    ElevatedCard(

    ) {
        Column(
            Modifier.fillMaxWidth(0.6f)
        ) {
            Row {
                Text (text= "Email")

            }
        }
    }
}