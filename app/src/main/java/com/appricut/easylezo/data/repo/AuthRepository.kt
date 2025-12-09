package com.appricut.easylezo.data.repo


// dependencies: firebase-auth-ktx, firebase-firestore-ktx

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
)  {

    // ثبت‌نام Email/Password و ساخت سند user در collection users
    suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw Exception("User is null after signup")
            val uid = user.uid

            // ساخت سند user با فیلدهای پایه (فقط server-side safe data)
            val userDoc = mapOf(
                "uid" to uid,
                "email" to email,
                "displayName" to displayName,
                "role" to "user" // نقش پیش‌فرض؛ قابل استفاده در rules یا بررسی داخل اپ
            )
            db.collection("users").document(uid).set(userDoc).await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ورود
    suspend fun signInWithEmail(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Sign-in failed")
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // خروج
    fun signOut() {
        auth.signOut()
    }

    // گرفتن کاربر جاری
    fun currentUserUid(): String? = auth.currentUser?.uid

    suspend fun isCurrentUserAdmin(): Boolean {
        val uid = auth.currentUser?.uid ?: return false
        return try {
            val doc = db.collection("users").document(uid).get().await()
            val role = doc.getString("role")
            role == "admin"
        } catch (e: Exception) {
            false
        }
    }

    // دریافت توکن و claims (بعد از set کردن custom claims)
    suspend fun getIdTokenResult(forceRefresh: Boolean = false): Map<String, Any?>? {
        val user = auth.currentUser ?: return null
        val tokenResult = user.getIdToken(forceRefresh).await()
        // tokenResult.claims is a Map<String, Any>
        return tokenResult.claims
    }
}
