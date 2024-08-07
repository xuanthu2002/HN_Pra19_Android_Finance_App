package com.example.fina.utils.base

import android.content.Context

interface ItemBase {
    fun getDisplayName(context: Context): String

    fun getInternalValue(): String
}
