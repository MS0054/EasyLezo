package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.AppLanguages
import kotlinx.coroutines.flow.Flow

interface AppLanguagesRepository {
    fun observeAppLanguages(): Flow<AppLanguages>
    suspend fun updateLocalAppLanguages(appLanguages: AppLanguages)
}