package com.example.translator_4_17.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.translator_4_17.db.DatabaseHolder.Companion.Db
import com.example.translator_4_17.db.data.HistoryItem
import com.example.translator_4_17.util.query


class HistoryModel : ViewModel() {

    var history by mutableStateOf(
        emptyList<HistoryItem>()
    )

    fun fetchHistory() {
        query {
            history = Db.historyDao().getAll().reversed()
        }
    }

    fun clearHistory() {
        query {
            Db.historyDao().deleteAll()
            history = listOf()
        }
    }

    fun deleteHistoryItem(historyItem: HistoryItem) {
        query {
            Db.historyDao().delete(historyItem)
        }
    }
}
