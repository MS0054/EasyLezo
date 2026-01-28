package com.appricut.easylezo.data.repo

import com.appricut.easylezo.data.model.Sentence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val categoriesCol = db.collection("Categories")
    private fun sentencesCol(categoryId: String) =
        categoriesCol.document(categoryId).collection("Sentences")

    suspend fun fetchSentences(categoryId: String): List<Sentence> {
        val snap = sentencesCol(categoryId).get().await()
        return snap.documents.map { doc ->
            Sentence(
                id = doc.id,
                ar = doc.getString("ar") ?: "",
                en = doc.getString("en") ?: "",
                fa = doc.getString("fa") ?: "",
                image = doc.getString("image") ?: "",
                level = doc.getString("level") ?: ""
            )
        }
    }

    suspend fun addSentence(categoryId: String, s: Sentence): String {
        val ref = sentencesCol(categoryId).add(
            mapOf(
                "ar" to s.ar,
                "en" to s.en,
                "fa" to s.fa,
                "image" to s.image,
                "level" to s.level
            )
        ).await()
        return ref.id
    }

    suspend fun updateSentence(categoryId: String, s: Sentence) {
        sentencesCol(categoryId).document(s.id).set(
            mapOf(
                "ar" to s.ar,
                "en" to s.en,
                "fa" to s.fa,
                "image" to s.image,
                "level" to s.level
            )
        ).await()
    }

    suspend fun deleteSentence(categoryId: String, sentenceId: String) {
        sentencesCol(categoryId).document(sentenceId).delete().await()
    }
}
