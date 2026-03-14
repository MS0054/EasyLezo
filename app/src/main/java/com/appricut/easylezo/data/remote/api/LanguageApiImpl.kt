package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.remote.model.LanguageDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LanguageApiImpl @Inject constructor (
    db: FirebaseFirestore
): LanguageApi {

    private val languagesCol = db.collection("Languages")
    private val metadataDoc = db.collection("metadata")
        .document("languages")

    override suspend fun getLanguages(): List<LanguageDto> {

        val snap = languagesCol
            .orderBy("order")
            .get()
            .await()

        return snap.documents.map { doc ->
            LanguageDto(
                id = doc.id,
                name = doc.getString("name") ?: "",
                code = doc.getString("code") ?: "",
                flag = doc.getString("flag") ?: "",
                isFromLanguage = doc.getBoolean("isFromLanguage") ?: false,
                isToLanguage = doc.getBoolean("isToLanguage") ?: false,
                order = (doc.getLong("order") ?: 0L).toInt()
            )
        }
    }
}
