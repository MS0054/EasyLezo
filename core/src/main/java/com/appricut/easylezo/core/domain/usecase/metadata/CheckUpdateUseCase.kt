package com.appricut.easylezo.core.domain.usecase.metadata

import com.appricut.easylezo.core.domain.model.UpdateResult
import com.appricut.easylezo.core.domain.model.UpdateType
import com.appricut.easylezo.core.domain.repository.AppInfoProvider
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckUpdateUseCase @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val appInfoProvider: AppInfoProvider
) {
    suspend operator fun invoke(): UpdateResult {
        val updateInfo = metadataRepository.observeMetadata().map { it.updateInfo }.first()
        val currentVersion = appInfoProvider.getVersionCode()

        val type = when {
            currentVersion < updateInfo.minVersionCode -> UpdateType.FORCE
            updateInfo.forcedVersions.split(",").contains(currentVersion.toString()) -> UpdateType.FORCE
            currentVersion < updateInfo.latestVersionCode -> UpdateType.OPTIONAL
            else -> UpdateType.NONE
        }
        return UpdateResult(type, updateInfo)
    }
}