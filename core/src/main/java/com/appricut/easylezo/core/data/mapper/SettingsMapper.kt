package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.remote.model.SettingsDto
import com.appricut.easylezo.core.domain.model.Settings

fun Settings.toDto() =
    SettingsDto( id, lastVersion, policyUrl, aboutUrl, termsUrl)
