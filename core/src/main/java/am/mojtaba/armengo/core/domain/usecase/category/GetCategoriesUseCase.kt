package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        val categoriesFlow = categoryRepository.observe()
        val appLanguagesFlow = appLanguagesRepository.observeAppLanguages()

        return categoriesFlow.combine(appLanguagesFlow) { categories, languages ->
            categories.map { category ->
                val fromText = category.translations.find { it.language == languages.from }?.text ?: ""
                val toText = category.translations.find { it.language == languages.to }?.text ?: ""

                category.copy(
                    fromText = fromText,
                    toText = toText
                )
            }
        }
    }
}