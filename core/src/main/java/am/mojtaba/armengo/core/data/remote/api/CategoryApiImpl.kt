package am.mojtaba.armengo.core.data.remote.api

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

    companion object {
        private const val COLLECTION = "Categories"
        private const val ORDER_FIELD = "order"
    }

    private val categoriesCol = db.collection(COLLECTION)

    override suspend fun getCategories(): List<CategoryDto> {
        val snap = categoriesCol.orderBy(ORDER_FIELD).get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(CategoryDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun syncCategories(categories: List<CategoryDto>) {
        if (categories.isEmpty()) return

        val batch = db.batch()
        categories.forEach { dto ->
            val docRef = categoriesCol.document(dto.id)
            if (dto.isDeleted) {
                batch.delete(docRef)
            } else {
                batch.set(docRef, dto, SetOptions.merge())
            }
        }
        batch.commit().await()
    }
}