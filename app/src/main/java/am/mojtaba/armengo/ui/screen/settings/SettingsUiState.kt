package am.mojtaba.armengo.ui.screen.settings

import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.User

data class SettingsUiState(
    val isLoading: Boolean = false,
    val settings: Settings = Settings(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val user: User? = null
)