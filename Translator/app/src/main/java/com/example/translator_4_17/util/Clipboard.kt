package com.example.translator_4_17.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

//剪贴板的操作：读入，查看，粘贴，复制
class Clipboard(context: Context) {
    private val clipboardManager = context.getSystemService(
        Context.CLIPBOARD_SERVICE
    ) as ClipboardManager

    fun write(text: String) {
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText(text, text)
        )
    }

    fun get(): String? {
        val clipboardItem = clipboardManager.primaryClip?.getItemAt(0) ?: return null
        return clipboardItem.text.toString()
    }

    fun hasClip() = clipboardManager.hasPrimaryClip()


}