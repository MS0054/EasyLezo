package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.core.data.remote.model.UserDto

interface UserApi {
    suspend fun getUser(uid: String): UserDto
    suspend fun updateUserAppLanguages(uid: String, appLanguagesDto: AppLanguagesDto)
}