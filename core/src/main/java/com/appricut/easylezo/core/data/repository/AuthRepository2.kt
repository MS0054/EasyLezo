package com.appricut.easylezo.core.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository2 @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User is null after signup")
            val userDoc = mapOf(
                "uid" to uid,
                "email" to email,
                "displayName" to displayName,
                "role" to "user"
            )
            db.collection("users").document(uid).set(userDoc).await()
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<String> {
        return try {
            val uid = auth.signInWithEmailAndPassword(email, password).await().user?.uid
                ?: throw Exception("Sign-in failed")
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun currentUserUid(): String? = auth.currentUser?.uid

    suspend fun isCurrentUserAdmin(): Boolean {
        val uid = auth.currentUser?.uid ?: return false
        return try {
            val doc = db.collection("users").document(uid).get().await()
            Log.i("hohoho","44444 " + doc.get("role"))
            doc.getString("role") == "admin"
        } catch (e: Exception) {
            false
        }
    }
}
