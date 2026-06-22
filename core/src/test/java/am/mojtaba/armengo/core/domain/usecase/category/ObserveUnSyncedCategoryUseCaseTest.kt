package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveUnSyncedCategoryUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var observeUnSyncedCategoryUseCase: ObserveUnSyncedCategoryUseCase

    @Before
    fun setUp() {
        categoryRepository = mockk()
        observeUnSyncedCategoryUseCase = ObserveUnSyncedCategoryUseCase(categoryRepository)
    }

    @Test
    fun `invoke should return unsynced status flow from repository`() = runTest {
        // Arrange
        val expectedStatus = true
        every { categoryRepository.observeUnsyncedStatus() } returns flowOf(expectedStatus)

        // Act & Assert
        observeUnSyncedCategoryUseCase().test {
            assertEquals(expectedStatus, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke should emit false when repository emits false`() = runTest {
        // Arrange
        val expectedStatus = false
        every { categoryRepository.observeUnsyncedStatus() } returns flowOf(expectedStatus)

        // Act & Assert
        observeUnSyncedCategoryUseCase().test {
            assertEquals(expectedStatus, awaitItem())
            awaitComplete()
        }
    }
}
