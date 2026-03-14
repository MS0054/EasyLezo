package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.data.remote.model.UserDto

interface UserApi {
    suspend fun getUser(uid: String): UserDto
    suspend fun updateUserAppLanguages(uid: String, appLanguagesDto: AppLanguagesDto)
}