package am.mojtaba.armengo.core.domain.model

data class Metadata(
    val id: Long = 0L,
    val lastUpdate: LastUpdate = LastUpdate(),
    val updateInfo: UpdateInfo = UpdateInfo(),
    val settings: Settings = Settings(),
    val errors: List<Error> = emptyList(),
    val resources: List<Resource> = emptyList(),
    val reportTypes: List<ReportType> = emptyList(),
    val reportStatusTypes: List<ReportStatusType> = emptyList(),
    val reportMessages: List<ReportMessage> = emptyList(),
    val appLanguages: AppLanguages = AppLanguages()
)
