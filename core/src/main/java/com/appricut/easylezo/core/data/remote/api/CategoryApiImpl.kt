package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.CategoryDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CategoryApiImpl @Inject constructor (
    private val db: FirebaseFirestore
): CategoryApi {

    private val categoriesCol = db.collection("Categories")



    override suspend fun getCategories(): List<CategoryDto> {

        val snap = categoriesCol
            .orderBy("order")
            .get()
            .await()

        return snap.documents.map { doc ->
            doc.reference.parent.id
            CategoryDto(
                id = doc.id,
                name = doc.getString("name") ?: "",
                image = doc.getString("image") ?: "",
                order = (doc.getLong("order") ?: 0L).toInt(),
            )


        }
    }
}
