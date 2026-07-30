package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.CategoryWordDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryWordApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CategoryWordApi {

    companion object {
        private const val COLLECTION = "CategoryWords"
    }
    private val collection = db.collection(COLLECTION)

    override suspend fun getCategoryWords(): List<CategoryWordDto> {
        val snap = collection.get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(CategoryWordDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun syncCategoryWords(categoryWords: List<CategoryWordDto>) {
        if (categoryWords.isEmpty()) return
        val batch = db.batch()
        categoryWords.forEach { dto ->
            val docRef = collection.document(dto.id)
            if (dto.isDeleted) {
                batch.delete(docRef)
            } else {
                batch.set(docRef, dto, SetOptions.merge())
            }
        }
        batch.commit().await()
    }
}
