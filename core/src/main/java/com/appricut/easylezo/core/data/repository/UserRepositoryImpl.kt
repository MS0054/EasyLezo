package com.appricut.easylezo.core.data.repository

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.appricut.easylezo.core.data.local.dao.UserDao
import com.appricut.easylezo.core.data.mapper.toDomain
import com.appricut.easylezo.core.data.mapper.toDto
import com.appricut.easylezo.core.data.mapper.toEntity
import com.appricut.easylezo.core.data.remote.api.UserApi
import com.appricut.easylezo.core.data.remote.model.AppLanguagesDto
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.model.User
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val metadataRepository: MetadataRepository,
    private val appLanguagesRepository: AppLanguagesRepository,
    private val userDao: UserDao,
    private val userApi: UserApi
) : UserRepository {

    override fun observeUser(): Flow<User> {
        return userDao.observeUser().map { user -> user?.toDomain() ?: User() }
    }

    override fun observeUsers(): Flow<List<User>> {
        val a = userDao.observeUsers()
            .map { list -> list?.map {
                it?.toDomain() ?: User()
            } ?: emptyList() }
        return a
    }
    override suspend fun searchUser(name: String?, email: String?): List<User> {
        return userApi.searchUsers(name, email).map { it.toDomain() }
    }

    override suspend fun syncUser(uid: String) {

        val metadata = metadataRepository.observeMetadata().first()
        if (metadata.lastUpdate.existNewUserData) {
            val newUserData = userApi.getUser(uid)

            userDao.clearAll()
            userDao.insert(newUserData.toEntity())

            if (newUserData.appLanguages == AppLanguagesDto()) {
                val appLanguages = appLanguagesRepository.observeAppLanguages().first()
                updateUserAppLanguagesServer(appLanguages)
            } else {
                appLanguagesRepository.updateLocalAppLanguages(newUserData.appLanguages.toDomain())
            }

            metadata.lastUpdate.existNewUserData = false
            metadataRepository.clearAndInsert(metadata)
        }
    }

    override suspend fun syncUsers(limit: Long) {
        val users = userApi.getUsers(limit)
        userDao.clearAll()
        userDao.insertAll(users.map { it.toEntity() })
    }

    override suspend fun updateUser(user: User) {
        userApi.updateUser(user.toDto())
        syncUsers(100)
    }



    override suspend fun updateUserAppLanguagesServer(appLanguages: AppLanguages) {
        val user = userDao.observeUser().firstOrNull()
        val uid = user?.uid ?: return // اگر UID وجود نداشت، عملیات متوقف می‌شود

        try {
            userApi.updateUserAppLanguages(uid, appLanguages.toDto())
        } catch (e: Exception) {
            throw e
        }

//        try {
        // ۱. ارسال درخواست به API
//            val response = userApi.updateAppLanguages(uid, appLanguages?.toDto())

//            if (response.isSuccessful) {
//                // ۲. (اختیاری) بروزرسانی متادیتا برای اینکه سینک بعدی بداند دیتا آپدیت شده است
//                val metadata = metadataDao.observeMetadata().first()
//                metadata.lastUpdate.existNewUserData = false // یا هر منطقی که برای ورژن‌بندی دارید
//                metadataDao.insertAll(metadata)
//            } else {
//                // مدیریت خطای پاسخ سرور (مثلاً Throw کردن یک Exception خاص)
//                throw Exception("Server update failed with code: ${response.code()}")
//            }
//        } catch (e: Exception) {
//             اینجا می‌توانید خطا را لاگ کنید یا به لایه بالاتر پاس دهید
//            throw e
//        }
    }

}