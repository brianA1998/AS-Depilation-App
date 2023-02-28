package com.example.depilationapp.presentation.clients.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.depilationapp.presentation.theme.teal200

@Composable
fun TextProvince(clientProvince : String) {
    Text(
        text = "De : $clientProvince",
        color = Color.White,
        fontSize = 15.sp,
    )
}