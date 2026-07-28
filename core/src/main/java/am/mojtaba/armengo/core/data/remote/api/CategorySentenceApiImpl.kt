package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.CategoryDto
import am.mojtaba.armengo.core.data.remote.model.CategorySentenceDto
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.forEach

@Singleton
class CategorySentenceApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : CategorySentenceApi {

    companion object {
        private const val COLLECTION = "CategorySentences"
    }
    private val collection = db.collection(COLLECTION)

    override suspend fun getCategorySentences(): List<CategorySentenceDto> {
        val snap = collection.get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(CategorySentenceDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun syncCategorySentences(categorySentences: List<CategorySentenceDto>) {
        if (categorySentences.isEmpty()) return
        val batch = db.batch()
        categorySentences.forEach { dto ->
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