package com.appricut.easylezo.core.data.remote.api

import com.appricut.easylezo.core.data.remote.model.SentenceDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceApiImpl @Inject constructor (
    private val db: FirebaseFirestore
): SentenceApi {

    private val sentencesCol = db.collection("Sentences")

    override suspend fun getSentences(): List<SentenceDto> {
        val snap = sentencesCol.get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(SentenceDto::class.java)?.copy(id = doc.id)
        }
    }
}
