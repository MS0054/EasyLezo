package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.domain.model.UpdateInfo
import am.mojtaba.armengo.core.domain.model.UpdateInfoDto

fun UpdateInfo.toDto() =
    UpdateInfoDto(latestVersion, latestVersionCode, minVersion, minVersionCode, forcedVersions, updateUrl, releaseNotes)
