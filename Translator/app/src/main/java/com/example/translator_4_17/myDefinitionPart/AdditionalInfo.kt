package com.example.translator_4_17.myDefinitionPart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator_4_17.util.Clipboard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdditionalInfo(
    title: String,
    text: String,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    Clipboard(context).write(text)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 12.sp
            )
            Spacer(
                modifier = Modifier.height(2.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }

        Icon(Icons.Default.ArrowUpward, null, Modifier.rotate(-45f))

        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
    }
}
