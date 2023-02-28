package com.example.depilationapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.depilationapp.presentation.clients.ClientsScreen
import com.example.depilationapp.presentation.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                ClientsScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClientsScreen()
}