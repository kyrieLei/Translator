package com.example.translator_4_17.ui.part

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.translator_4_17.R
import com.example.translator_4_17.db.data.MenuItemData
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.myDefinitionPart.AccentColorPrefDialog
import com.example.translator_4_17.myDefinitionPart.StyledIconButton
import com.example.translator_4_17.myDefinitionPart.ThemeModeDialog
import com.example.translator_4_17.myDefinitionPart.TopBarMenu
import com.example.translator_4_17.util.Clipboard
import com.example.translator_4_17.util.speechRecognition


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    mainModel: TranslationModel,
    menuItems: List<MenuItemData>
) {
    val context = LocalContext.current
    val handler = Handler(Looper.getMainLooper())
    var showThemeOptions by remember {
        mutableStateOf(false)
    }

    var showAccentColorDialog by remember {
        mutableStateOf(false)
    }


    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        actions = {
            if (mainModel.insertedText == "") {
                StyledIconButton(
                    imageVector = Icons.Default.Mic
                ) {
                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        speechRecognition.checkPermission(context as Activity)
                        return@StyledIconButton
                    }

                    speechRecognition.recognizeSpeech(context as Activity) {
                        mainModel.insertedText = it
                        mainModel.enqueueTranslation()
                    }

                }
            }
            StyledIconButton(
                imageVector = Icons.Default.LightMode
            ) {
                showThemeOptions = true
            }
            StyledIconButton(
                imageVector = Icons.Default.Palette
            ) {
                showAccentColorDialog = true
            }


            var copyImageVector by remember {
                mutableStateOf(Icons.Default.ContentCopy)
            }

            if (mainModel.translation.translatedText != "") {
                StyledIconButton(
                    imageVector = copyImageVector,
                    onClick = {
                        Clipboard(
                            context
                        ).write(
                            mainModel.translation.translatedText
                        )
                        copyImageVector = Icons.Default.DoneAll
                        handler.postDelayed({
                            copyImageVector = Icons.Default.ContentCopy
                        }, 2000)
                    }
                )
            }
            if (mainModel.insertedText != "") {
                StyledIconButton(
                    imageVector = Icons.Default.Clear,
                    onClick = {
                        mainModel.clearTranslation()
                    }
                )
            }
            TopBarMenu(menuItems)
        }
    )

    if (showAccentColorDialog) {
        AccentColorPrefDialog {
            showAccentColorDialog = false
        }
    }
    if (showThemeOptions) {
        ThemeModeDialog {
            showThemeOptions = false
        }
    }

}
