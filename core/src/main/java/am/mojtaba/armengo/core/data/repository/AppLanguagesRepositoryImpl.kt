package am.mojtaba.armengo.core.data.repository
import am.mojtaba.armengo.core.data.local.dao.AppLanguagesDao
import am.mojtaba.armengo.core.data.mapper.toDomain
import am.mojtaba.armengo.core.data.mapper.toEntity
import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
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
