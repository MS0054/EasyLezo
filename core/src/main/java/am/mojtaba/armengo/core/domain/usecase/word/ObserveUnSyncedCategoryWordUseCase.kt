package am.mojtaba.armengo.core.domain.usecase.word

import am.mojtaba.armengo.core.domain.repository.CategoryWordRepository
import javax.inject.Inject

class ObserveUnSyncedCategoryWordUseCase @Inject constructor(
    private val repository: CategoryWordRepository
) {
    operator fun invoke() = repository.observeUnsynced()
}
