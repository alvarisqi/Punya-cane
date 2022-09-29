package com.example.githubuserapp3.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey

object DataStore {

    const val DATA_STORE_NAME = "USER_DATASTORE"
    val DATA_STORE_PREF_THEM_KEY = booleanPreferencesKey("THEME_PREF_KEY")
}