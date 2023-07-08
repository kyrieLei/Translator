package com.example.translator_4_17.util


import androidx.compose.ui.graphics.Color

fun query(dbQuery: () -> Unit) {
    Thread {
        dbQuery.invoke()
    }.start()
}

fun <T> awaitQuery(action: () -> T): T {
    var value: T? = null
    Thread {
        value = action.invoke()
    }.apply {
        start()
        join()
    }
    return value!!
}

fun String.hexToColor() = Color(android.graphics.Color.parseColor("#$this"))
