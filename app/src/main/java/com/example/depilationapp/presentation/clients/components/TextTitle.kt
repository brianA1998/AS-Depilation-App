package com.example.depilationapp.presentation.clients.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.depilationapp.presentation.theme.teal200

@Composable
fun TextTitle(clientTitle: String) {
    Text(
        text = clientTitle,
        color = Color.White,
        fontSize = 24.sp,
    )
}