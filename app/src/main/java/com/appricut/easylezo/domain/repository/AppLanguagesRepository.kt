package com.appricut.easylezo.domain.repository

import com.appricut.easylezo.domain.model.AppLanguages
import com.appricut.easylezo.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface AppLanguagesRepository {
    fun observeAppLanguages(): Flow<AppLanguages>
    suspend fun updateLocalAppLanguages(appLanguages: AppLanguages)
}