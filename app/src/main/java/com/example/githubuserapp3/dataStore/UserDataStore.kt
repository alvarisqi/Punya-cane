package com.example.githubuserapp3.dataStore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import java.util.concurrent.Flow
import java.util.prefs.Preferences

class UserDataStore(private val contex: Contex) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: UserDataStore? = null

        fun getInstance(context: Context): UserDataStore = mInstance?: synchronized(this) {
            val newInstance = mInstance?: UserDataStore(context).also { mInstance = it }
            newInstance
        }
    }

    private val Context.userPreferenceDataStore : DataStore<Preferences> by preferencesDataStore(
        name =  DataStore.DATA_STORE_NAME
    )

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        contex.userPreferenceDataStore.edit{
            it[DataStore.DATA_STORE_PREF_THEM_KEY] = isDarkModeActive
        }
    }

    fun getThemeSetting(): Flow<Boolean> =
        contex.userPreferenceDataStore.data.map {
            it[DataStore.DATA_STORE_PREF_THEM_KEY] ?: false
        }
}