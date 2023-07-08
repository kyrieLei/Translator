package com.example.translator_4_17.ui.screen

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.translator_4_17.R
import com.example.translator_4_17.db.data.MenuItemData
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.myDefinitionPart.LanguageSelector

import com.example.translator_4_17.ui.part.Sentence
import com.example.translator_4_17.ui.part.TopBar
import com.example.translator_4_17.ui.part.TranslationPart


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    navHostController: NavController,
    viewModel: TranslationModel,
) {
    val context = LocalContext.current
    val playerUnhappy = MediaPlayer.create(LocalContext.current, R.raw.unhappy)
    val playerHappy = MediaPlayer.create(LocalContext.current, R.raw.happy)

    LaunchedEffect(Unit) {
        viewModel.refresh(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                mainModel = viewModel,
                menuItems = listOf(
                    MenuItemData(
                        text = stringResource(
                            id = R.string.options
                        ),
                        icon = Icons.Default.Menu
                    ) {
                        navHostController.navigate("settings")
                    },
                    MenuItemData(
                        text = stringResource(
                            id = R.string.Happy
                        ),
                        icon = Icons.Filled.Mood,
                    ) {
                        if (playerHappy.isPlaying) {
                            playerHappy.pause()
                        } else {
                            playerHappy.start()
                        }
                        if (playerUnhappy.isPlaying) {
                            playerUnhappy.pause()
                        }
                    },
                    MenuItemData(
                        text = stringResource(
                            id = R.string.Unhappy
                        ),
                        icon = Icons.Default.MoodBad,
                    ) {
                        if (playerUnhappy.isPlaying) {
                            playerUnhappy.pause()
                        } else {
                            playerUnhappy.start()
                        }
                        if (playerHappy.isPlaying) {
                            playerHappy.pause()
                        }
                    },
                )


            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .weight(1.0f)
                ) {
                    TranslationPart(navHostController, viewModel)
                }
                Column {
                    Sentence()
                }
                Row(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguageSelector(
                        viewModel.availableLanguages,
                        viewModel.sourceLanguage,
                        autoLanguageEnabled = true,
                        viewModel = viewModel
                    ) {
                        Log.d("Language", "${viewModel.availableLanguages}")
                        viewModel.sourceLanguage = it
                    }

                    val switchBtnEnabled by mutableStateOf(
                        viewModel.sourceLanguage.code != ""
                    )

                    IconButton(
                        onClick = {
                            if (viewModel.availableLanguages.isEmpty()) return@IconButton
                            if (!switchBtnEnabled) return@IconButton
                            val temp = viewModel.sourceLanguage
                            viewModel.sourceLanguage = viewModel.targetLanguage
                            viewModel.targetLanguage = temp
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_switch),
                            null,
                            modifier = Modifier
                                .size(18.dp),
                            tint = if (switchBtnEnabled) MaterialTheme.colorScheme.onSurface else Color.Gray
                        )
                    }
                    LanguageSelector(
                        viewModel.availableLanguages,
                        viewModel.targetLanguage,
                        viewModel = viewModel
                    ) {
                        Log.d("Language", "${viewModel.availableLanguages}")
                        viewModel.targetLanguage = it
                    }
                }
            }
        }
    }
}
