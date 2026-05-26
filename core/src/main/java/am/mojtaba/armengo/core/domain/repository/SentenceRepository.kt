package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.Sentence
import kotlinx.coroutines.flow.Flow

interface SentenceRepository {

    fun observe(categoryId: String): Flow<List<Sentence>>
    fun observeUnsynced(): Flow<Boolean>
    suspend fun syncFromServer(isForce: Boolean )
    suspend fun addSentenceLocal(sentence: Sentence)
    suspend fun updateSentenceLocal(sentence: Sentence)
    suspend fun deleteSentenceLocal(id: String)
    suspend fun sortSentenceLocal(sentences: List<Sentence>)
    suspend fun downloadVoice(sentences: List<Sentence>)

}