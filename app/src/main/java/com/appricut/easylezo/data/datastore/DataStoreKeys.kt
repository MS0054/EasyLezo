package com.appricut.easylezo.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val LANGUAGE = stringPreferencesKey("language")

    val IS_NEW_LANGUAGE_EXIST = stringPreferencesKey("is_new_language_exist")
    val THEME = stringPreferencesKey("theme")
}
