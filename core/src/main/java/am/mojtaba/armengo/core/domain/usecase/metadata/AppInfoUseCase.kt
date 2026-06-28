package am.mojtaba.armengo.core.domain.usecase.metadata

import am.mojtaba.armengo.core.domain.repository.AppInfoProvider
import javax.inject.Inject

class AppInfoUseCase @Inject constructor(
    private val appInfoProvider: AppInfoProvider
) {
    operator fun invoke() {
        appInfoProvider.getVersionCode()
    }
}