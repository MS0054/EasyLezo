package am.mojtaba.armengo.core.data.remote.api

import android.util.Log
import am.mojtaba.armengo.core.data.remote.model.CategoryDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

    override suspend fun syncCategories(categories: List<CategoryDto>) {
        Log.i("CategoryApiImpl", "syncCategories: $categories")
        val batch = db.batch()
        categories.forEach { dto ->
            val docRef = categoriesCol.document(dto.id)
            if (dto.isDeleted) {
                batch.delete(docRef)
            } else {
                val dataMap = mutableMapOf<String, Any?>(
                    "id" to dto.id,
                    "order" to dto.order,
                    "createdAt" to dto.createdAt,
                    "updatedAt" to dto.updatedAt,
                    "image" to dto.image,
                    "translations" to dto.translations
                )
                batch.set(docRef, dataMap, SetOptions.merge())
            }
        }
        batch.commit().await()
    }
}
