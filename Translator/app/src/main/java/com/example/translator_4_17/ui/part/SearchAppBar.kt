package com.example.translator_4_17.ui.part

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.translator_4_17.R
import com.example.translator_4_17.myDefinitionPart.StyledIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    var isSearchViewVisible by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        navigationIcon = {
            if (!isSearchViewVisible) navigationIcon()
        },
        title = {
            Text(title)
        },
        actions = {
            Crossfade(isSearchViewVisible) {
                when (it) {
                    true -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 15.dp)
                        ) {
                            val focusManager = LocalFocusManager.current
                            val focusRequester = remember { FocusRequester() }

                            SideEffect {
                                focusRequester.requestFocus()
                            }
                            StyledIconButton(
                                modifier = Modifier.padding(top = 10.dp),
                                imageVector = Icons.Default.ArrowBack
                            ) {
                                isSearchViewVisible = false
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedTextField(
                                modifier = Modifier
                                    .focusRequester(focusRequester)
                                    .weight(1f),
                                value = value,
                                onValueChange = onValueChange,
                                label = {
                                    Text(text = stringResource(R.string.search))
                                },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(Icons.Default.Search, null)
                                }
                            )
                        }
                    }

                    else -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StyledIconButton(imageVector = Icons.Default.Search) {
                                isSearchViewVisible = true
                            }
                            actions.invoke(this)
                        }
                    }
                }
            }
        }
    )
}
