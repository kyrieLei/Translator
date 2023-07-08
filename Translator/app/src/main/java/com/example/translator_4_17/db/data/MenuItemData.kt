package com.example.translator_4_17.db.data

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    val text: String,
    val icon: ImageVector,
    val action: () -> Unit
)
