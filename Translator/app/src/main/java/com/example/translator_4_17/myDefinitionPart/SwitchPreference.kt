package com.example.translator_4_17.myDefinitionPart

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.translator_4_17.util.Preferences

@Composable
fun SwitchPreference(
    preferenceKey: String,
    defaultValue: Boolean,
    preferenceTitle: String,
    onValueChange: (Boolean) -> Unit = {}
) {
    var checked by remember {
        mutableStateOf(
            Preferences.get(
                preferenceKey,
                defaultValue
            )
        )
    }

    fun onCheckedChange(newValue: Boolean) {
        checked = newValue
        Preferences.put(
            preferenceKey,
            checked
        )
        onValueChange.invoke(newValue)
    }

    val indicationSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .border(2.dp, Color.LightGray, RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(5.dp, 3.dp)
            .clickable(
                indicationSource,
                null
            ) {
                onCheckedChange(!checked)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        PreferenceItem(
            title = preferenceTitle,
            modifier = Modifier
                .weight(1.0f)
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(!checked)
            }
        )
    }
}
