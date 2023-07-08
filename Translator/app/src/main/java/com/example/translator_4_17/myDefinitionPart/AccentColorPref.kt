package com.example.translator_4_17.myDefinitionPart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.example.translator_4_17.R
import com.example.translator_4_17.ui.MainActivity
import com.example.translator_4_17.ui.theme.defaultAccentColor
import com.example.translator_4_17.util.Preferences
import com.example.translator_4_17.util.hexToColor
import okhttp3.internal.toHexString

@Composable
fun AccentColorPrefDialog(
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    var color by remember {
        mutableStateOf(
            Preferences.getAccentColor()
        )
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            DialogButton(text = stringResource(R.string.okay)) {
                Preferences.prefs.edit(true) { putString(Preferences.accentColorKey, color) }
                (context as MainActivity).accentColor = color
                onDismissRequest.invoke()
            }
        },
        dismissButton = {
            DialogButton(text = stringResource(R.string.cancel)) {
                onDismissRequest.invoke()
            }
        },
        title = {
            Text(stringResource(R.string.accent_color))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.go_back)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = { newValue ->
                            color = defaultAccentColor.takeIf { !newValue }
                        }
                    )
                }

                Row(
                    modifier = Modifier.height(250.dp)
                ) {
                    AnimatedVisibility(
                        visible = color != null
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            listOf("R", "G", "B").forEachIndexed { index, c ->
                                val startIndex = index * 2
                                color?.let {
                                    ColorSlider(
                                        label = c,
                                        value = it.substring(startIndex, startIndex + 2).toInt(16),
                                        onChange = { colorInt ->
                                            var newHex = colorInt.toHexString()
                                            if (newHex.length == 1) newHex = "0$newHex"
                                            color = StringBuilder(it).apply {
                                                setCharAt(startIndex, newHex[0])
                                                setCharAt(startIndex + 1, newHex[1])
                                            }.toString()
                                        }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            color?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier.size(50.dp).background(
                                            MaterialTheme.colorScheme.primary,
                                            CircleShape
                                        )
                                    )
                                    Text(text = "   --->   ", fontSize = 20.sp)
                                    Box(
                                        modifier = Modifier.size(50.dp).background(
                                            it.hexToColor(),
                                            CircleShape
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
