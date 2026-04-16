package com.appricut.easylezo.core.data.remote.api

import android.util.Log
import com.appricut.easylezo.core.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.data.remote.model.SentenceDto
import com.appricut.easylezo.core.data.remote.model.UserDto
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : UserApi {

    private val usersCol = db.collection("users")


    override suspend fun getUser(uid: String): UserDto {
        val user = usersCol.document(uid).get().await()
        return user.toObject(UserDto::class.java) ?: UserDto()
    }

    override suspend fun getUsers(limit: Long): List<UserDto> {
        val users = usersCol.limit(limit).get().await()
        return users.documents.mapNotNull { doc ->
            doc.toObject(UserDto::class.java)
        }
    }

    override suspend fun updateUser(userDto: UserDto) {
        try {
            usersCol.document(userDto.uid).set(userDto).await()
            } catch (e: Exception) {
                throw e
            }

    }


    override suspend fun searchUsers(name: String?, email: String?): List<UserDto> {
        var query: Query = usersCol

        if (!name.isNullOrBlank()) {
            query = query.whereEqualTo("displayName", name)

        } else if (!email.isNullOrBlank()) {
            query = query.whereGreaterThanOrEqualTo("email", email)
                .whereLessThanOrEqualTo("email", email + "\uf8ff")
        } else {
            query = usersCol.limit(100)
        }



        return try {
            val snapshot = query.get().await()
            val users = snapshot.toObjects(UserDto::class.java)
            Log.i("searchUsers", "Found: ${users.size} users")
            users
        } catch (e: Exception) {
            Log.e("searchUsers", "Query failed: ${e.message}")
            emptyList()
        }
    }

    override suspend fun updateUserAppLanguages(uid: String, appLanguagesDto: AppLanguagesDto) {
        try {
            usersCol.document(uid).update("appLanguages", appLanguagesDto).await()
            // استفاده از update برای تغییر فقط یک فیلد خاص
        } catch (e: Exception) {
            // مدیریت خطا (مثلاً عدم دسترسی یا قطعی اینترنت)
            throw e
        }
    }
}