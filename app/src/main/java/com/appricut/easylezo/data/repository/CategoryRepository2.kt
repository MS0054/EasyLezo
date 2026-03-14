package com.appricut.easylezo.data.repository

import com.appricut.easylezo.domain.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository2 @Inject constructor(
    private val db: FirebaseFirestore
) {

    private val categoriesCol = db.collection("Categories")

    private val categoriesOrderedQuery = categoriesCol.orderBy("order")
    suspend fun fetchAllCategories(): List<Category> {
        val snap = categoriesOrderedQuery.get().await()
        return snap.documents.map { doc ->
            Category(
                id = doc.id,
                name = doc.getString("name") ?: "",
                image = doc.getString("image") ?: "",
                order = (doc.getLong("order") ?: 0L).toInt()
            )
        }
    }

    suspend fun addCategory(category: Category): String {
        val snap = categoriesCol.get().await()
        val maxOrder = snap.documents
            .mapNotNull { it.getLong("order") }
            .maxOrNull() ?: -1

        val ref = categoriesCol.add(mapOf(
            "name" to category.name,
            "image" to category.image,
            "order" to maxOrder + 1
        )).await()
        return ref.id
    }



        suspend fun updateCategory(category: Category) {
            categoriesCol.document(category.id).update(
                mapOf("name" to category.name, "image" to category.image)
            ).await()
        }

        suspend fun deleteCategory(categoryId: String) {
            categoriesCol.document(categoryId).delete().await()
        }

    suspend fun updateCategoriesOrder(categories: List<Category>) {
        val batch = db.batch()

        categories.forEach { cat ->
            batch.update(
                categoriesCol.document(cat.id),
                "order",
                cat.order
            )
        }

        batch.commit().await()
    }
}
