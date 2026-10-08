package am.mojtaba.armengo.core.data.local.entity

import am.mojtaba.armengo.core.domain.model.Error
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.UpdateInfo

@Entity(tableName = "metadata")
data class MetadataEntity(
    @PrimaryKey val id: Long = 0L,

    @Embedded(prefix = "lastUpdate_")
    var lastUpdate: LastUpdate = LastUpdate(),

    @Embedded(prefix = "updateInfo_")
    val updateInfo: UpdateInfo = UpdateInfo(),

    @Embedded(prefix = "settings_")
    val settings: Settings = Settings(),

    val errors: List<Error> = emptyList(),

    // this field has specific TypeConverter instead of Embedded
    val resources: List<Resource> = emptyList(),

    val reportTypes: List<ReportType> = emptyList(),

    val reportStatusTypes: List<ReportStatusType> = emptyList(),

    val reportMessages: List<ReportMessage> = emptyList(),

    @Embedded(prefix = "appLanguage_")
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)