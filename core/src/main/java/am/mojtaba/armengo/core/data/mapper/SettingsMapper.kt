package am.mojtaba.armengo.core.data.mapper

import am.mojtaba.armengo.core.data.remote.model.SettingsDto
import am.mojtaba.armengo.core.domain.model.Settings

fun Settings.toDto() =
    SettingsDto( id, lastVersion, policyUrl, aboutUrl, termsUrl)
