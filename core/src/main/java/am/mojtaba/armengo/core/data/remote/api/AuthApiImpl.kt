package am.mojtaba.armengo.core.data.remote.api

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApiImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    @ApplicationContext private val context: Context
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

    override suspend fun signInWithGoogle(idToken: String): String {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        val user = result.user ?: throw IllegalStateException("Google sign-in failed")
        val uid = user.uid

        // Check if user exists in Firestore, if not create
        val userDoc = db.collection("users").document(uid).get().await()
        if (!userDoc.exists()) {
            val newUser = mapOf(
                "uid" to uid,
                "email" to (user.email ?: ""),
                "displayName" to (user.displayName ?: ""),
                "role" to "user"
            )
            db.collection("users").document(uid).set(newUser).await()
        }

        return uid
    }

    override suspend fun signOut() {
        auth.signOut()
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            GoogleSignIn.getClient(context, gso).signOut().await()
        } catch (e: Exception) {
            // Ignore Google sign out errors
        }
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