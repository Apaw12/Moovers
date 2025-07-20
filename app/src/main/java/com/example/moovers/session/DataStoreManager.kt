package com.example.moovers.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_session")

class DataStoreManager(private val context: Context) {

    companion object {
        private val USER_ID_KEY = intPreferencesKey("USER_ID")
    }

    // Ambil userId (default = -1)
    val userId: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY] ?: -1
    }

    // Simpan userId
    suspend fun saveUserId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = id
        }
    }

    // Hapus session
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }
}
