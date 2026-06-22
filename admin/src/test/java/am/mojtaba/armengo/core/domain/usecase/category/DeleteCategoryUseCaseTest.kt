package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class DeleteCategoryUseCaseTest {

    @MockK
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var deleteCategoryUseCase : DeleteCategoryUseCase


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        deleteCategoryUseCase = DeleteCategoryUseCase(categoryRepository)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `invoke should call deleteCategoryLocal in repository with correct category id` ()= runTest {
        val fakeCategoryId = "123"

        coEvery { categoryRepository.deleteCategoryLocal(fakeCategoryId) } returns Unit
        deleteCategoryUseCase(fakeCategoryId)
        coVerify(exactly = 1) { categoryRepository.deleteCategoryLocal(fakeCategoryId) }
    }

}