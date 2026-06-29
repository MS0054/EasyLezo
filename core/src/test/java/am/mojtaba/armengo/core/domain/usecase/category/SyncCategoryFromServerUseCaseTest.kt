package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SyncCategoryFromServerUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase

    @Before
    fun setUp() {
        categoryRepository = mockk()
        syncCategoryFromServerUseCase = SyncCategoryFromServerUseCase(categoryRepository)
    }

    @Test
    fun `invoke should call syncLocal on repository and return success`() = runTest {
        // Arrange
        val isForce = true
        coEvery { categoryRepository.syncLocal(isForce) } returns Result.success(Unit)

        // Act
        val result = syncCategoryFromServerUseCase(isForce)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { categoryRepository.syncLocal(isForce) }
        confirmVerified(categoryRepository)
    }

    @Test
    fun `invoke should call syncLocal on repository and return failure when repository fails`() = runTest {
        // Arrange
        val isForce = false
        val exception = Exception("Network error")
        coEvery { categoryRepository.syncLocal(isForce) } returns Result.failure(exception)

        // Act
        val result = syncCategoryFromServerUseCase(isForce)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { categoryRepository.syncLocal(isForce) }
        confirmVerified(categoryRepository)
    }

    @Test
    fun `invoke should use default isForce value as false`() = runTest {
        // Arrange
        coEvery { categoryRepository.syncLocal(false) } returns Result.success(Unit)

        // Act
        syncCategoryFromServerUseCase()

        // Assert
        coVerify(exactly = 1) { categoryRepository.syncLocal(false) }
    }
}
