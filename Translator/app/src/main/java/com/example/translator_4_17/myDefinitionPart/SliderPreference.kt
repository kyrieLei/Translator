package com.example.translator_4_17.myDefinitionPart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.translator_4_17.util.Preferences


@Composable
fun SliderPreference(
    preferenceKey: String,
    preferenceTitle: String,
    defaultValue: Float,
    minValue: Float,
    maxValue: Float,
    stepSize: Float
) {
    var value by remember {
        mutableStateOf(
            Preferences.get(
                preferenceKey,
                defaultValue
            )
        )
    }

    Column(
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp))
            .padding(6.dp, 15.dp)
    ) {
        PreferenceItem(
            title = preferenceTitle,
        )

        Spacer(
            modifier = Modifier.width(1.dp)
        )
        SliderWithLabel(
            value = value,
            onValueChange = {
                value = it
                Preferences.put(
                    preferenceKey,
                    it
                )
            },
            valueRange = minValue..maxValue,
            steps = getStepCount(
                minValue,
                maxValue,
                stepSize
            )
        )
    }
}

private fun getStepCount(minValue: Float, maxValue: Float, stepSize: Float): Int {
    return ((maxValue - minValue) / stepSize).toInt() - 1
}
