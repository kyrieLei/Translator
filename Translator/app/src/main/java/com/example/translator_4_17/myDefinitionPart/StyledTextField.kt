package com.example.translator_4_17.myDefinitionPart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    text: String,
    placeholder: String? = null,
    readOnly: Boolean = false,
    fontSize: TextUnit = 23.sp,
    textColor: Color = MaterialTheme.typography.bodyMedium.color,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = text,
        onValueChange = {
            onValueChange.invoke(it)
        },
        readOnly = readOnly,
        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(2.dp)).fillMaxHeight().fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent
        ),
        placeholder = {
            if (placeholder != null) {
                Text(
                    text = placeholder,
                    fontSize = fontSize,
                )
            }
        },
        textStyle = TextStyle(
            fontSize = fontSize,
            color = textColor
        )
    )
}


