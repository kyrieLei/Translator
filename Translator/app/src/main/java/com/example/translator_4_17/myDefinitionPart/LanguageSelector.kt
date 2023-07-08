package com.example.translator_4_17.myDefinitionPart

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translator_4_17.R
import com.example.translator_4_17.db.DatabaseHolder
import com.example.translator_4_17.db.data.Language

import com.example.translator_4_17.models.TranslationModel
import com.example.translator_4_17.ui.part.SearchAppBar
import com.example.translator_4_17.util.query


@Composable
fun LanguageSelector(
    availableLanguages: List<Language>,
    selectedLanguage: Language,
    autoLanguageEnabled: Boolean = false,
    viewModel: TranslationModel,
    onClick: (Language) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    ElevatedButton(
        modifier = Modifier
            .padding(8.dp),
        onClick = { showDialog = !showDialog },
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            text = selectedLanguage.name
        )
    }

    val languages = availableLanguages.toMutableList()


    Log.d("Language", "$languages")

    if (showDialog) {
        var searchQuery by remember {
            mutableStateOf("")
        }

        FullscreenDialog(
            onDismissRequest = {
                showDialog = false
            },
            topBar = {
                SearchAppBar(
                    title = "",
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    navigationIcon = {
                        StyledIconButton(
                            imageVector = Icons.Default.ArrowBack,
                            onClick = { showDialog = false }
                        )
                    },
                    actions = {}
                )
            },
            content = {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 20.dp, bottom = 30.dp),
                        text = stringResource(
                            if (autoLanguageEnabled) R.string.translate_from else R.string.translate_to
                        ),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    LazyColumn {
                        if (autoLanguageEnabled) {
                            item {
                                val autoLanguage = Language("", stringResource(R.string.auto))
                                LanguageItem(
                                    language = autoLanguage,
                                    isPinned = null,
                                    selectedLanguage = selectedLanguage,
                                    onClick = {
                                        onClick.invoke(autoLanguage)
                                        viewModel.enqueueTranslation()
                                    },
                                    onPinnedChange = {}
                                )
                            }
                        }

                        val bookmarks = viewModel.bookmarkedLanguages.filter {
                            validateFilter(it, searchQuery)
                        }
                        if (bookmarks.isNotEmpty()) {
                            item {
                                Text(
                                    modifier = Modifier.padding(
                                        top = 20.dp,
                                        bottom = 10.dp,
                                        start = 15.dp
                                    ),
                                    text = stringResource(R.string.favorites).uppercase(),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                        items(bookmarks) {
                            LanguageItem(
                                language = it,
                                isPinned = viewModel.bookmarkedLanguages.contains(it),
                                selectedLanguage = selectedLanguage,
                                onClick = {
                                    onClick.invoke(it)
                                    viewModel.enqueueTranslation()
                                },
                                onPinnedChange = {
                                    viewModel.bookmarkedLanguages =
                                        viewModel.bookmarkedLanguages.filter { language ->
                                            it != language
                                        }
                                    query {
                                        DatabaseHolder.Db.languageBookmarksDao().delete(it)
                                    }
                                }
                            )
                        }

                        item {
                            Text(
                                modifier = Modifier.padding(
                                    top = 20.dp,
                                    bottom = 10.dp,
                                    start = 15.dp
                                ),
                                text = stringResource(R.string.all_languages).uppercase(),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 12.sp
                            )
                        }

                        items(
                            languages.filter {
                                validateFilter(it, searchQuery)
                            }
                        ) {
                            LanguageItem(
                                language = it,
                                isPinned = viewModel.bookmarkedLanguages.contains(it),
                                selectedLanguage = selectedLanguage,
                                onClick = {
                                    onClick.invoke(it)
                                    viewModel.enqueueTranslation()
                                },
                                onPinnedChange = {
                                    viewModel.bookmarkedLanguages =
                                        if (viewModel.bookmarkedLanguages.contains(it)) {
                                            query {
                                                DatabaseHolder.Db.languageBookmarksDao().delete(it)
                                            }
                                            viewModel.bookmarkedLanguages - it
                                        } else {
                                            query {
                                                DatabaseHolder.Db.languageBookmarksDao()
                                                    .insertAll(it)
                                            }
                                            viewModel.bookmarkedLanguages + it
                                        }
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}

private fun validateFilter(language: Language, query: String): Boolean {
    if (query == "") return true
    val lowerQuery = query.lowercase()
    return language.name.lowercase().contains(lowerQuery) ||
            language.code.lowercase().contains(lowerQuery)
}


