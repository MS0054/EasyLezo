package am.mojtaba.armengo.core.data.repository

import am.mojtaba.armengo.core.data.remote.api.AuthApi
import am.mojtaba.armengo.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
//    نکته: استفاده از runCatching بهت اجازه میده خروجی‌ها رو به صورت تمیز تبدیل به Result کنی تا در لایه‌های بالاتر مدیریت خطا راحت‌تر باشه.
    override suspend fun signUp(email: String, password: String, displayName: String): Result<String> =
        runCatching { authApi.signUpWithEmail(email, password, displayName) }

    override suspend fun signIn(email: String, password: String): Result<String> =
        runCatching { authApi.signInWithEmail(email, password) }

    override suspend fun signOut() = authApi.signOut()

    override suspend fun isCurrentUserAdmin(): Boolean = authApi.isCurrentUserAdmin()

    override fun getCurrentUserUid(): String? = authApi.currentUserUid().ifEmpty { null }




//    private val _isAdmin = MutableStateFlow<Boolean?>(null)
//
//    override suspend fun checkAdmin() {
//        _isAdmin.value = authApi.isCurrentUserAdmin()
//    }
//
//    override fun isAdmin(): Flow<Boolean?> = _isAdmin
}