package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SortCategoryUseCaseTest {

    @MockK
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var sortCategoryUseCase: SortCategoryUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sortCategoryUseCase = SortCategoryUseCase(categoryRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should call sortCategoryLocal in repository with correct categories`() = runTest {
        val fakeCategories = listOf(
            Category(
                id = "123",
                order = 1,
                translations = emptyList(),
                fromText = "Hello",
                toText = "Բարև"
            ),
            Category(
                id = "124",
                order = 2,
                translations = emptyList(),
                fromText = "Hello",
                toText = "Բարև"
            )
        )


        coEvery { categoryRepository.sortCategoryLocal(fakeCategories) } returns Unit
        categoryRepository.sortCategoryLocal(fakeCategories)
        coVerify(exactly = 1) { categoryRepository.sortCategoryLocal(fakeCategories) }

    }
}