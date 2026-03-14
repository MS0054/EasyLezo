package com.appricut.easylezo.data.repository
import com.appricut.easylezo.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.data.mapper.toDomain
import com.appricut.easylezo.data.mapper.toEntity
import com.appricut.easylezo.domain.model.AppLanguages
import com.appricut.easylezo.domain.repository.AppLanguagesRepository
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
            .map {  it.toDomain() }
    }

    override suspend fun updateLocalAppLanguages(appLanguages: AppLanguages) {
        appLanguagesDao.insertAll(appLanguages.toEntity() )
    }

}
