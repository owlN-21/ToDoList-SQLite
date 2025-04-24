package com.example.todolistnavigation.features

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todolistnavigation.ui.theme.mainColor
import java.net.URLDecoder

@Composable
fun InformationItem(item: String) {
    val decodedItem = try {
        URLDecoder.decode(item, "UTF-8")
    } catch (e: Exception) {
        "Invalid item"
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        colors = CardDefaults.cardColors(
            containerColor = mainColor
        )
    ) {
        Text(
            text = decodedItem,
            modifier = Modifier.padding(16.dp)
        )
    }
}