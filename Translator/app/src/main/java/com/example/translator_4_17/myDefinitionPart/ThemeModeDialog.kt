package com.example.translator_4_17.myDefinitionPart

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.translator_4_17.R
import com.example.translator_4_17.constant.ThemeMode
import com.example.translator_4_17.db.data.ListPreferenceOption
import com.example.translator_4_17.ui.MainActivity
import com.example.translator_4_17.util.Preferences


@Composable
fun ThemeModeDialog(
    onDismiss: () -> Unit
) {
    val activity = LocalContext.current as MainActivity
    ListPreferenceDialog(
        preferenceKey = Preferences.themeModeKey,
        onDismissRequest = {
            onDismiss.invoke()
        },
        options = listOf(
            ListPreferenceOption(
                name = stringResource(R.string.theme_auto),
                value = ThemeMode.AUTO
            ),
            ListPreferenceOption(
                name = stringResource(R.string.theme_light),
                value = ThemeMode.LIGHT
            ),
            ListPreferenceOption(
                name = stringResource(R.string.theme_dark),
                value = ThemeMode.DARK
            )
        ),
        onOptionSelected = {
            activity.themeMode = it.value
        }
    )
}
