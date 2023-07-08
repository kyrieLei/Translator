package com.example.translator_4_17.ui.part

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.translator_4_17.R
import com.example.translator_4_17.models.HistoryModel
import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.myDefinitionPart.StyledIconButton

@Composable
fun HistoryPart(
    navController: NavController,
    translationModel: TranslationModel
) {
    val imageModifier = Modifier
        .size(500.dp)
        .alpha(0.2f)
    val viewModel: HistoryModel = viewModel()

    var searchQuery by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        viewModel.fetchHistory()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),

        {
            SearchAppBar(
                title = stringResource(R.string.history),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                navigationIcon = {
                    StyledIconButton(
                        imageVector = Icons.Default.History
                    ) {
                        navController.popBackStack()
                    }
                },
                actions = {
                    StyledIconButton(imageVector = Icons.Default.Delete) {
                        viewModel.clearHistory()
                    }
                }
            )
        }


    ) { pV ->
        Box(
            modifier = Modifier
                .padding(pV)
        ) {
            val query = searchQuery.lowercase()
            val filteredHistory = viewModel.history.filter {
                it.insertedText.lowercase().contains(query) ||
                        it.translatedText.lowercase().contains(query)
            }
            if (filteredHistory.isNotEmpty()) {
                LazyColumn {
                    items(filteredHistory) {
                        HistoryRow(navController, translationModel, it) {
                            viewModel.history = viewModel.history.filter { item ->
                                it.id != item.id
                            }
                            viewModel.deleteHistoryItem(it)
                        }
                    }
                }
            } else {

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.slam_dunk),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = imageModifier
                    )
                }

            }
        }
    }
}
