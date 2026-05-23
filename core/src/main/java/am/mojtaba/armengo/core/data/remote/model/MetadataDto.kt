package am.mojtaba.armengo.core.data.remote.model

import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo

data class MetadataDto(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val updateInfo: UpdateInfo = UpdateInfo(),
    val settings: Settings = Settings(),
    val resources: List<Resource> = emptyList(),
    val appLanguages: AppLanguagesDto = AppLanguagesDto()
)