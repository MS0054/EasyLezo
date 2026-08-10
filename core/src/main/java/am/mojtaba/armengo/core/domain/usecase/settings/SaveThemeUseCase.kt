package am.mojtaba.armengo.core.domain.usecase.settings

import am.mojtaba.armengo.core.data.datastore.AppDataStore
import am.mojtaba.armengo.core.data.datastore.enums.ThemeMode
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val appDataStore: AppDataStore
) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        appDataStore.saveTheme(themeMode.name)
    }
}
