package com.appricut.easylezo.core.domain.model

import androidx.room.Embedded
import com.appricut.easylezo.core.data.local.entity.AppLanguagesEntity


data class Settings(
    val id: Long = 0L,
    @Embedded(prefix = "appLanguagesEntity_")
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)