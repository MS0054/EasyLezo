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

    // --- Fetch All Categories ---
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

    // --- Add Category ---
    suspend fun addCategory(category: Category): String {
        val map = mapOf(
            "name" to category.name,
            "image" to category.image
        )
        val ref = categoriesCol.add(map).await()
        return ref.id
    }

    // --- Update Category ---
    suspend fun updateCategory(category: Category) {
        val map = mapOf(
            "name" to category.name,
            "image" to category.image
        )
        categoriesCol.document(category.id).set(map).await()
    }

    // --- Delete Category ---
    suspend fun deleteCategory(categoryId: String) {
        categoriesCol.document(categoryId).delete().await()
    }
}
