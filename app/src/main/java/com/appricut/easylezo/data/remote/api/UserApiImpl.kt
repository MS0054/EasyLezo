package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.data.remote.model.UserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserApi {
    private val userDb = db.collection("users")

    override suspend fun getUser(uid: String): UserDto {
        return userDb.document(uid).get().await().toObject(UserDto::class.java) ?: UserDto()
    }

    override suspend fun updateUserAppLanguages(uid: String, appLanguagesDto: AppLanguagesDto) {
        try {
            // استفاده از update برای تغییر فقط یک فیلد خاص
            userDb.document(uid).update("appLanguages", appLanguagesDto).await()
        } catch (e: Exception) {
            // مدیریت خطا (مثلاً عدم دسترسی یا قطعی اینترنت)
            throw e
        }
    }
}