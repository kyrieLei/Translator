package com.example.translator_4_17.ui.part

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator_4_17.constant.quates

@Composable
fun Sentence() {
    // 定义一个美句列表

    var quote by remember { mutableStateOf(quates.qua.random()) }
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable {
                quote = quates.qua.random()
            }
            .shadow(ambientColor = Color.Cyan, elevation = 6.dp),
    ) {
        Text(
            text = quote,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(fontSize = 18.sp),
        )
    }
}