package com.example.depilationapp.presentation.clients.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.depilationapp.core.Constants
import com.example.depilationapp.data.model.Client
import com.example.depilationapp.presentation.theme.graySurface


@Composable
fun ClientCard(client: Client) {

    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                TextTitle(clientTitle = client.name ?: Constants.NO_VALUE)
                TextProvince(clientProvince = client.province ?: Constants.NO_VALUE)
            }
        }
    }
}