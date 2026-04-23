package com.appricut.easylezo.core.data.repository
import android.util.Log
import androidx.compose.ui.platform.LocalView
import com.appricut.easylezo.core.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLanguagesRepositoryImpl @Inject constructor(
    private val appLanguagesDao: AppLanguagesDao
) : AppLanguagesRepository {

    override fun observeAppLanguages(): Flow<AppLanguages> {
        return appLanguagesDao.observeAppLanguages()
            .map { it?.toDomain() ?: AppLanguages() }
    }

    override suspend fun updateLocalAppLanguages(appLanguages: AppLanguages) {
        appLanguagesDao.insertAll(appLanguages.toEntity() )
    }

}
