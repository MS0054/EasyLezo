package com.appricut.easylezo.core.data.mapper

import com.appricut.easylezo.core.data.remote.model.LastUpdateDto
import com.appricut.easylezo.core.domain.model.LastUpdate

fun LastUpdate.toDto() =
    LastUpdateDto( language, category, sentence)
