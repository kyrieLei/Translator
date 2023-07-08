package com.example.translator_4_17.ui.part

import android.annotation.SuppressLint
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.example.translator_4_17.R
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.myDefinitionPart.ButtonWithIcon
import com.example.translator_4_17.myDefinitionPart.StyledIconButton
import com.example.translator_4_17.myDefinitionPart.StyledTextField
import com.example.translator_4_17.util.*
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun TranslationPart(
    navController: NavController,
    viewModel: TranslationModel,
) {

    val context = LocalContext.current
    val view = LocalView.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val clipboard = Clipboard(
        LocalContext.current.applicationContext
    )
    var hasClip by remember {
        mutableStateOf(
            clipboard.hasClip()
        )
    }

    var isKeyboardOpen by remember {
        mutableStateOf(false)
    }
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }


    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1.0f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                StyledTextField(
                    text = viewModel.insertedText,
                    placeholder = stringResource(R.string.enter_text)
                ) {
                    viewModel.insertedText = it
                    if (it == "") hasClip = clipboard.hasClip()
                    viewModel.enqueueTranslation()
                }

                val modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)

                if (viewModel.translating) {
                    LinearProgressIndicator(
                        modifier = modifier
                    )
                } else {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier
                            .size(70.dp, 3.dp)
                    )
                }
                if (viewModel.translation.translatedText != "") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        StyledIconButton(
                            imageVector = Icons.Default.VolumeUp
                        ) {
                            tts.speak(
                                context,
                                viewModel.translation.translatedText,
                                viewModel.targetLanguage.code
                            )
                        }
                    }
                }

                if (hasClip && viewModel.insertedText.isBlank()) {
                    Row {
                        ButtonWithIcon(
                            text = stringResource(R.string.paste),
                            icon = Icons.Default.ContentPaste
                        ) {
                            viewModel.insertedText = clipboard.get() ?: ""
                            viewModel.enqueueTranslation()
                        }

                        Spacer(
                            modifier = Modifier
                                .width(0.dp)
                        )

                    }
                } else if (
                    viewModel.insertedText.isNotBlank() &&
                    !Preferences.get(Preferences.translateAutomatically, true)
                ) {
                    ButtonWithIcon(
                        text = stringResource(R.string.translate),
                        icon = Icons.Default.Translate
                    ) {
                        viewModel.translateNow()
                    }
                }


                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                //加入历史记录，向有道一样
                if (viewModel.translation.translatedText.isEmpty()) {
                    Column(modifier = Modifier.size(500.dp)) {
                        HistoryPart(navController = navController, translationModel = viewModel)
                    }

                } else {
                    StyledTextField(
                        text = viewModel.translation.translatedText,
                        readOnly = true,
                        textColor = MaterialTheme.typography.bodyMedium.color
                    )
                }
            }
            if (scrollState.value > 100) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    }
                ) {
                    Icon(Icons.Default.ArrowUpward, null)
                }
            }
        }
        if (Preferences.get(
                Preferences.showAdditionalInfo,
                true
            ) && !isKeyboardOpen
        ) {
            AdditionalInfoComponent(viewModel.translation, viewModel)
        }
        Spacer(
            modifier = Modifier
                .height(15.dp)
        )
    }
}

