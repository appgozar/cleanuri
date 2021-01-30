package com.example.test.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Util {
    @JvmStatic
    fun copyToClipboard(context: Context, text: String){
        val clipboard = (context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager) ?: return
        val clip = ClipData.newPlainText("Text", text)
        clipboard.setPrimaryClip(clip)
    }
    @JvmStatic
    fun hideKeyboard(context: Context, focusedView : View){
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        try { imm?.hideSoftInputFromWindow(focusedView.windowToken, 0) } catch (e: Exception) { }
    }
}