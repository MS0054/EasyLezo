package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.model.Word
import am.mojtaba.armengo.core.domain.repository.WordRepository
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(word: Word) = wordRepository.addWordLocal(word)
}