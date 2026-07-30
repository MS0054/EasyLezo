package am.mojtaba.armengo.core.domain.repository

import am.mojtaba.armengo.core.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun observe(categoryId: String): Flow<List<Word>>
    fun observe(): Flow<List<Word>>
    fun observeUnsynced(): Flow<Boolean>
    suspend fun syncFromServer(isForce: Boolean ): Result<Unit>
    suspend fun addWordLocal(word: Word)
    suspend fun updateWordLocal(word: Word)
    suspend fun deleteWordLocal(id: String)
    suspend fun sortWordLocal(words: List<Word>)
    suspend fun downloadVoice(words: List<Word>)

}