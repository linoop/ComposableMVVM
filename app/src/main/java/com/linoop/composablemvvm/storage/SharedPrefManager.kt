package com.linoop.composablemvvm.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.linoop.composablemvvm.utils.Constants.TOKEN
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun setToken(token: String) = sharedPreferences.edit { putString(TOKEN, token) }
    fun getToken() = sharedPreferences.getString(TOKEN, "")
}