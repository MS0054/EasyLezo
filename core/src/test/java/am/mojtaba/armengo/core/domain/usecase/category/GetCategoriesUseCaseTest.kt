package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.model.Translate
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var appLanguagesRepository: AppLanguagesRepository
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setUp() {
        categoryRepository = mockk()
        appLanguagesRepository = mockk()
        getCategoriesUseCase = GetCategoriesUseCase(categoryRepository, appLanguagesRepository)
    }

    @Test
    fun `invoke should combine categories and app languages and map fromText and toText correctly`() = runTest {
        // Arrange
        val languages = AppLanguages(from = "English", to = "Armenian")
        val translations = listOf(
            Translate(language = "English", text = "Hello"),
            Translate(language = "Armenian", text = "Barev")
        )
        val category = Category(id = "1", translations = translations)
        
        every { categoryRepository.observe() } returns flowOf(listOf(category))
        every { appLanguagesRepository.observeAppLanguages() } returns flowOf(languages)

        // Act & Assert
        getCategoriesUseCase().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Hello", result[0].fromText)
            assertEquals("Barev", result[0].toText)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should handle missing translations with empty strings`() = runTest {
        // Arrange
        val languages = AppLanguages(from = "English", to = "French")
        val translations = listOf(
            Translate(language = "English", text = "Hello")
        )
        val category = Category(id = "1", translations = translations)
        
        every { categoryRepository.observe() } returns flowOf(listOf(category))
        every { appLanguagesRepository.observeAppLanguages() } returns flowOf(languages)

        // Act & Assert
        getCategoriesUseCase().test {
            val result = awaitItem()
            assertEquals("Hello", result[0].fromText)
            assertEquals("", result[0].toText)
            awaitComplete()
        }
    }
}
