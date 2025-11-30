package com.appricut.easylezo.Repo

import com.appricut.easylezo.data.Category
import com.appricut.easylezo.data.Sentence
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getCategories(): List<Category> {
        val snapshot = db.collection("Categories").get().await()
        return snapshot.documents.map { doc ->
            val category = doc.toObject(Category::class.java)
            // id سند را اضافه کن
            category?.copy(id = doc.id) ?: Category()
        }
    }

    suspend fun getSentences(categoryName: String): List<Sentence> {
        val snapshot = db.collection("Categories")
            .document(categoryName)  // فرض می‌کنیم documentId همون name باشه
            .collection("Sentences")
            .get()
            .await()
        return snapshot.documents.map { it.toObject(Sentence::class.java)!! }
    }
}