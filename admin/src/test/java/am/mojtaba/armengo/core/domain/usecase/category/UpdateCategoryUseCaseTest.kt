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

class UpdateCategoryUseCaseTest {

    @MockK
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var updateCategoryUseCase: UpdateCategoryUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        updateCategoryUseCase = UpdateCategoryUseCase(categoryRepository)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `invoke should call updateCategory in repository with correct category`() = runTest {

        val fakeCategory = Category(
            id = "123",
            order = 1,
            translations = emptyList(),
            fromText = "Hello",
            toText = "Բարև"
        )

        coEvery { categoryRepository.updateCategoryLocal(fakeCategory) } returns Unit

        updateCategoryUseCase(fakeCategory)

        coVerify(exactly = 1){ categoryRepository.updateCategoryLocal(fakeCategory) }

    }

}