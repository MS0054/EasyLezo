package am.mojtaba.armengo.core.domain.usecase.settings

import am.mojtaba.armengo.core.data.datastore.AppDataStore
import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val appDataStore: AppDataStore
) {
    operator fun invoke(): Flow<ThemeMode> {
        return appDataStore.themeFlow.map { themeName ->
            try {
                if (themeName != null) ThemeMode.valueOf(themeName) else ThemeMode.SYSTEM
            } catch (e: Exception) {
                ThemeMode.SYSTEM
            }
        }
    }
}
