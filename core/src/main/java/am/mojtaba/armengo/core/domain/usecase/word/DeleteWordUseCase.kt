package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.repository.WordRepository
import javax.inject.Inject

class DeleteWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(id: String) = wordRepository.deleteWordLocal(id)
}