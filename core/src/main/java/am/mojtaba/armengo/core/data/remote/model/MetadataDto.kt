package am.mojtaba.armengo.core.data.remote.model

import am.mojtaba.armengo.core.domain.model.Error
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo

data class MetadataDto(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val updateInfo: UpdateInfo = UpdateInfo(),
    val settings: Settings = Settings(),
    val errors: List<Error> = emptyList(),
    val resources: List<Resource> = emptyList(),
    val reportTypes: List<ReportType> = emptyList(),
    val reports: List<Report> = emptyList(),
    val reportMessages: List<ReportMessage> = emptyList(),
    val appLanguages: AppLanguagesDto = AppLanguagesDto()
)