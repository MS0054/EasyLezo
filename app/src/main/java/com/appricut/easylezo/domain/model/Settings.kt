package com.appricut.easylezo.domain.model

import androidx.room.Embedded
import com.appricut.easylezo.data.local.entity.AppLanguagesEntity


data class Settings(
    val id: Long = 0L,
    @Embedded(prefix = "appLanguagesEntity_")
    val appLanguages: AppLanguagesEntity = AppLanguagesEntity()
)