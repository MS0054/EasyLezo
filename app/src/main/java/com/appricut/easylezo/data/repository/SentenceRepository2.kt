package com.appricut.easylezo.data.repository

import com.appricut.easylezo.domain.model.Sentence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceRepository2 @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val categoriesCol = db.collection("Categories")

    private fun sentencesCol(categoryId: String) = categoriesCol.document(categoryId).collection("Sentences")

    private fun sentencesOrderedQuery(categoryId: String) = sentencesCol(categoryId).orderBy("order")

    // ================= FETCH =================
    suspend fun fetchSentences(categoryId: String): List<Sentence> {
        val snap = sentencesOrderedQuery(categoryId).get().await()
        return snap.documents.map { doc ->
            Sentence(
                id = doc.id,
                ar = doc.getString("ar") ?: "",
                en = doc.getString("en") ?: "",
                fa = doc.getString("fa") ?: "",
                image = doc.getString("image") ?: "",
                level = doc.getString("level") ?: "",
                order = (doc.getLong("order") ?: 0L).toInt()
            )
        }
    }

    // ================= ADD =================
    suspend fun addSentence(categoryId: String, s: Sentence): String {
        val snap = sentencesCol(categoryId).get().await()

        val maxOrder = snap.documents
            .mapNotNull { it.getLong("order") }
            .maxOrNull() ?: -1

        val ref = sentencesCol(categoryId).add(
            mapOf(
                "ar" to s.ar,
                "en" to s.en,
                "fa" to s.fa,
                "image" to s.image,
                "level" to s.level,
                "order" to maxOrder + 1
            )
        ).await()

        return ref.id
    }

    // ================= UPDATE =================
    suspend fun updateSentence(categoryId: String, s: Sentence) {
        sentencesCol(categoryId).document(s.id).update(
            mapOf(
                "ar" to s.ar,
                "en" to s.en,
                "fa" to s.fa,
                "image" to s.image,
                "level" to s.level
            )
        ).await()
    }

    // ================= DELETE =================
    suspend fun deleteSentence(categoryId: String, sentenceId: String) {
        sentencesCol(categoryId).document(sentenceId).delete().await()
    }

    // ================= UPDATE ORDER =================
    suspend fun updateSentencesOrder(
        categoryId: String,
        sentences: List<Sentence>
    ) {
        val batch = db.batch()

        sentences.forEach { sentence ->
            batch.update(
                sentencesCol(categoryId).document(sentence.id),
                "order",
                sentence.order
            )
        }

        batch.commit().await()
    }
}
