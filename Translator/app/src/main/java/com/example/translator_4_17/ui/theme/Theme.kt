package com.example.translator_4_17.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import com.example.translator_4_17.constant.ThemeMode
import com.example.translator_4_17.util.hexToColor


const val defaultAccentColor = "d0bccf"

@Composable
fun TranslatorTheme(
    themeMode: Int = ThemeMode.AUTO,
    accentColor: Color? = null,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.AUTO -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        else -> true
    }

    val colorScheme = when {
        accentColor == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> {
            val accent = accentColor ?: defaultAccentColor.hexToColor()
            val blendColor = if (darkTheme) android.graphics.Color.WHITE else android.graphics.Color.BLACK
            val onPrimary = Color(ColorUtils.blendARGB(accent.toArgb(), blendColor, 0.3f))
            if (darkTheme) darkColorScheme(accent, onPrimary, secondary = onPrimary) else lightColorScheme(
                accent,
                onPrimary,
                secondary = onPrimary
            )
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            activity.window.navigationBarColor = colorScheme.background.toArgb()
            activity.window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(
                activity.window,
                view
            ).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(
                activity.window,
                view
            ).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
