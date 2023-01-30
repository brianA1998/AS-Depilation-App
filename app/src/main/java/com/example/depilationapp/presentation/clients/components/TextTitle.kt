package com.example.depilationapp.presentation.clients.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(clientTitle: String) {
    Text(
        text = clientTitle,
        color = Color.DarkGray,
        fontSize = 24.sp
    )
}