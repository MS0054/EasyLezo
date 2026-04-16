package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.appricut.easylezo.core.data.remote.model.LanguageDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CategoryApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CategoryApi {

    private val categoriesCol = db.collection("Categories")


    override suspend fun getCategories(): List<CategoryDto> {
        val snap = categoriesCol.orderBy("order").get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(CategoryDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun addCategory(category: CategoryDto) {
        try {
            categoriesCol.add(category).await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun updateCategory(category: CategoryDto) {
        try {
            categoriesCol.document(category.id).set(category).await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun deleteCategory(category: CategoryDto) {
        try {
            categoriesCol.document(category.id).delete().await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun sortCategories(categories: List<CategoryDto>) {
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
