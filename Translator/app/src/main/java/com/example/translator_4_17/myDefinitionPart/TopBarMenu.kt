package com.example.translator_4_17.myDefinitionPart

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.*
import com.example.translator_4_17.db.data.MenuItemData

@Composable
fun TopBarMenu(
    menuItems: List<MenuItemData>
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box {

        StyledIconButton(
            imageVector = Icons.Default.MoreVert,
            onClick = {
                expanded = true
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            menuItems.forEach { menuItemData ->
                DropDownItem(menuItemData) { newState ->
                    expanded = newState
                }
            }
        }
    }
}
