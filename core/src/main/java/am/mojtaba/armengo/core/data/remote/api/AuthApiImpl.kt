package am.mojtaba.armengo.core.data.remote.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApiImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthApi {

    override fun currentUserUid(): String = auth.currentUser?.uid ?: ""

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        displayName: String
    ): String {
        val result = auth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val uid = result.user?.uid
            ?: throw IllegalStateException("User is null after signup")

        val userDoc = mapOf(
            "uid" to uid,
            "email" to email,
            "displayName" to displayName,
            "role" to "user"
        )

        db.collection("users")
            .document(uid)
            .set(userDoc)
            .await()

        return uid
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): String {
        val uid = auth
            .signInWithEmailAndPassword(email, password)
            .await()
            .user
            ?.uid
            ?: throw IllegalStateException("Sign-in failed")

        return uid
    }

    override fun signOut() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun isCurrentUserAdmin(): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        return try {
            val doc = db
                .collection("users")
                .document(uid)
                .get()
                .await()


            doc.getString("role") == "admin"
        } catch (e: Exception) {
            false
        }
    }
}