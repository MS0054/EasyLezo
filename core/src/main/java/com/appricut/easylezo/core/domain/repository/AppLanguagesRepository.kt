package com.appricut.easylezo.core.domain.repository

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface AppLanguagesRepository {
    fun observeAppLanguages(): Flow<AppLanguages>
    suspend fun updateLocalAppLanguages(appLanguages: AppLanguages)
}