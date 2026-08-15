package am.mojtaba.armengo.core.data.remote.api


interface AuthApi {

    suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String
    ): String

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): String

    suspend fun signInWithGoogle(idToken: String): String

    suspend fun signOut()

     fun currentUserUid(): String

    suspend fun isCurrentUserAdmin(): Boolean
}