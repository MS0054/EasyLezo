package com.appricut.easylezo.data.repo

import com.appricut.easylezo.data.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val categoriesCol = db.collection("categories")

    suspend fun fetchAllCategories(): List<Category> {
        val snap = categoriesCol.get().await()
        return snap.documents.map { doc ->
            Category(
                id = doc.id,
                name = doc.getString("name") ?: "",
                image = doc.getString("image") ?: ""
            )
        }
    }

    suspend fun addCategory(category: Category): String {
        val ref = categoriesCol.add(mapOf(
            "name" to category.name,
            "image" to category.image
        )).await()
        return ref.id
    }

    suspend fun updateCategory(category: Category) {
        categoriesCol.document(category.id).set(
            mapOf("name" to category.name, "image" to category.image)
        ).await()
    }

    suspend fun deleteCategory(categoryId: String) {
        categoriesCol.document(categoryId).delete().await()
    }
}
