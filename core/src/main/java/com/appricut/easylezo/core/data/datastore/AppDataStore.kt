package com.appricut.easylezo.core.data.datastore

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.appricut.easylezo.core.domain.model.Language
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.appDataStore by preferencesDataStore(name = "app_settings")

class AppDataStore(private val context: Context) {
    // ---------- Save Functions ----------

    suspend fun saveLanguage(language: String) {
        context.appDataStore.edit { prefs ->
            prefs[DataStoreKeys.LANGUAGE] = language
        }
    }

    suspend fun saveTheme(theme: String) {
        context.appDataStore.edit { prefs ->
            prefs[DataStoreKeys.THEME] = theme
        }
    }

    suspend fun saveIsNewLanguageExist(isNewLanguageExist: String) {
        context.appDataStore.edit { prefs ->
            prefs[DataStoreKeys.IS_NEW_LANGUAGE_EXIST] = isNewLanguageExist
        }
    }


    // ---------- Read Flows ----------

    val languageFlow: Flow<String?> =
        context.appDataStore.data.map { prefs ->
            prefs[DataStoreKeys.LANGUAGE]
        }

    val themeFlow: Flow<String?> =
        context.appDataStore.data.map { prefs ->
            prefs[DataStoreKeys.THEME]
        }

    val isNewLanguageExistFlow: Flow<String?> =
        context.appDataStore.data.map { prefs ->
            prefs[DataStoreKeys.IS_NEW_LANGUAGE_EXIST]
        }


    // ---------- Clear All ----------

    suspend fun clearAll() {
        context.appDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
