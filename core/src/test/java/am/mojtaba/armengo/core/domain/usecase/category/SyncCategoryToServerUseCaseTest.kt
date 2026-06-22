package am.mojtaba.armengo.core.domain.usecase.category

import am.mojtaba.armengo.core.domain.manager.SyncManager
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SyncCategoryToServerUseCaseTest {

    private lateinit var syncManager: SyncManager
    private lateinit var syncCategoryToServerUseCase: SyncCategoryToServerUseCase

    @Before
    fun setUp() {
        syncManager = mockk(relaxed = true)
        syncCategoryToServerUseCase = SyncCategoryToServerUseCase(syncManager)
    }

    @Test
    fun `invoke should call syncCategoryToServer on syncManager with correct workerTag`() {
        // Arrange
        val workerTag = "test_worker_tag"

        // Act
        syncCategoryToServerUseCase(workerTag)

        // Assert
        verify(exactly = 1) { syncManager.syncCategoryToServer(workerTag) }
        confirmVerified(syncManager)
    }
}
