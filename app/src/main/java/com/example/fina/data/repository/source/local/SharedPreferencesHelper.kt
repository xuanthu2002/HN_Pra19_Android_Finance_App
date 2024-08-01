package com.example.fina.data.repository.source.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper private constructor(
    context: Context,
    name: String?,
    mode: Int,
) {
    fun save(
        key: String,
        value: String,
    ) {
        editor.putString(key, value).apply()
    }

    fun get(key: String): String? {
        return preferences.getString(key, null)
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    fun clear() {
        editor.clear().apply()
    }

    companion object {
        private var instance: SharedPreferencesHelper? = null
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        const val PREF_FAVOURITE = "favourites"
        const val SEPARATOR_CHARACTER = ";"

        fun getInstance(
            context: Context,
            name: String? = null,
            mode: Int = Context.MODE_PRIVATE,
        ): SharedPreferencesHelper =
            synchronized(this) {
                instance ?: SharedPreferencesHelper(context, name, mode).also {
                    instance = it
                }
            }
    }

    init {
        preferences = context.getSharedPreferences(name, mode)
        editor = preferences.edit()
    }
}
