package com.example.translator_4_17.ui.part

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.translator_4_17.R
import com.example.translator_4_17.db.data.HistoryItem
import com.example.translator_4_17.models.TranslationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryRow(
    navController: NavController,
    translationModel: TranslationModel,
    historyItem: HistoryItem,
    onDelete: () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    fun loadTranslation() {
        showDialog = false
        translationModel.insertedText = historyItem.insertedText
        translationModel.translateNow()
        navController.navigate("home")
    }


    val maxLines = 3

    val state = rememberDismissState(
        confirmValueChange = {
            when (it) {
                DismissValue.DismissedToEnd -> {
                    onDelete.invoke()
                    true
                }

                else -> false
            }
        }
    )

    SwipeToDismiss(
        state = state,
        background = {},
        dismissContent = {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        loadTranslation()
                    },
                overlineContent = {
                    Text("${historyItem.sourceLanguageName} -> ${historyItem.targetLanguageName}")
                },
                headlineContent = {
                    Text(
                        historyItem.translatedText,
                        fontSize = 18.sp,
                        maxLines = maxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                supportingContent = {
                    Text(
                        historyItem.insertedText,
                        fontSize = 14.sp,
                        maxLines = maxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
        directions = setOf(DismissDirection.StartToEnd)
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            text = {
                Column {
                    Text(
                        historyItem.insertedText,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )

                    Text(
                        historyItem.translatedText,
                        fontSize = 14.sp
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        loadTranslation()
                    }
                ) {
                    Text(stringResource(R.string.open))
                }
            }
        )
    }
}
