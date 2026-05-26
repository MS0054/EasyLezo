package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.LanguageDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LanguageApiImpl @Inject constructor (
    private val db: FirebaseFirestore
): LanguageApi {

    private val languagesCol = db.collection("Languages")

    override suspend fun getLanguages(): List<LanguageDto> {
        val snap = languagesCol.orderBy("order").get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(LanguageDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun syncLanguages(languages: List<LanguageDto>) {
        val batch = db.batch()
        languages.forEach { dto ->
            val docRef = languagesCol.document(dto.id)
            if (dto.isDeleted) {
                batch.delete(docRef)
            } else {
                val dataMap = mutableMapOf<String, Any?>(
                    "id" to dto.id,
                    "name" to dto.name,
                    "code" to dto.code,
                    "flag" to dto.flag,
                    "isFromLanguage" to dto.isFromLanguage,
                    "isToLanguage" to dto.isToLanguage,
                    "order" to dto.order,
                    "createdAt" to dto.createdAt,
                    "updatedAt" to dto.updatedAt
                )
                batch.set(docRef, dataMap, SetOptions.merge())
            }
        }
        batch.commit().await()
    }

//    override suspend fun addLanguage(language: LanguageDto) {
//        try {
//            languagesCol.add(language).await()
//        } catch (e: Exception) {
//            // manage network error
//            throw e
//        }
//    }
//
//    override suspend fun updateLanguage(language: LanguageDto) {
//        try {
//            languagesCol.document(language.id).set(language).await()
//        } catch (e: Exception) {
//            // manage network error
//            throw e
//        }
//    }
//
//    override suspend fun deleteLanguage(language: LanguageDto) {
//        try {
//            languagesCol.document(language.id).delete().await()
//        } catch (e: Exception) {
//            // manage network error
//            throw e
//        }
//    }
//
//    override suspend fun sortLanguages(languages: List<LanguageDto>) {
//        val batch = db.batch()
//
//        languages.forEach { cat ->
//            batch.update(
//                languagesCol.document(cat.id),
//                "order",
//                cat.order
//            )
//        }
//
//        batch.commit().await()
//    }
}
