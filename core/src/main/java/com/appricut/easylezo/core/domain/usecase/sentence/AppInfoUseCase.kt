package com.appricut.easylezo.core.domain.usecase.sentence

import com.appricut.easylezo.core.domain.repository.AppInfoProvider
import javax.inject.Inject

class AppInfoUseCase @Inject constructor(
    private val appInfoProvider: AppInfoProvider
) {
    operator fun invoke() {
        appInfoProvider.getVersionCode()
    }
}