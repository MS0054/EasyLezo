package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.domain.model.UpdateInfo
import com.appricut.easylezo.core.domain.model.UpdateInfoDto

fun UpdateInfo.toDto() =
    UpdateInfoDto(latestVersion, latestVersionCode, minVersion, minVersionCode, forcedVersions, updateUrl, releaseNotes)
