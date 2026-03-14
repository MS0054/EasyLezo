package com.appricut.easylezo.data.remote.api


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

    fun signOut()

     fun currentUserUid(): String

    suspend fun isCurrentUserAdmin(): Boolean
}