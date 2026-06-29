package am.mojtaba.armengo.admin.ui.screen.category

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.usecase.category.*
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryVTest {

    private val getCategoriesUseCase: GetCategoriesUseCase = mockk()
    private val addCategoryUseCase: AddCategoryUseCase = mockk()
    private val updateCategoryUseCase: UpdateCategoryUseCase = mockk()
    private val deleteCategoryUseCase: DeleteCategoryUseCase = mockk()
    private val sortCategoryUseCase: SortCategoryUseCase = mockk()
    private val syncCategoryToServerUseCase: SyncCategoryToServerUseCase = mockk()
    private val syncCategoryFromServerUseCase: SyncCategoryFromServerUseCase = mockk()
    private val observeUnSyncedCategoryUseCase: ObserveUnSyncedCategoryUseCase = mockk()

    private lateinit var viewModel: CategoryV
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        // Mocking default behaviors for init block
        every { getCategoriesUseCase() } returns flowOf(emptyList())
        every { observeUnSyncedCategoryUseCase() } returns flowOf(false)
        
        viewModel = CategoryV(
            getCategoriesUseCase,
            addCategoryUseCase,
            updateCategoryUseCase,
            deleteCategoryUseCase,
            sortCategoryUseCase,
            syncCategoryToServerUseCase,
            syncCategoryFromServerUseCase,
            observeUnSyncedCategoryUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should observe categories and update uiState`() = runTest {
        val categories = listOf(Category(id = "1", fromText = "Test"))
        val categoriesFlow = MutableSharedFlow<List<Category>>()
        every { getCategoriesUseCase() } returns categoriesFlow
        
        // Re-init to capture the new flow
        viewModel = CategoryV(
            getCategoriesUseCase,
            addCategoryUseCase,
            updateCategoryUseCase,
            deleteCategoryUseCase,
            sortCategoryUseCase,
            syncCategoryToServerUseCase,
            syncCategoryFromServerUseCase,
            observeUnSyncedCategoryUseCase
        )

        viewModel.categoryUiState.test {
            // Check initial/loading state if needed, then emit data
            categoriesFlow.emit(categories)
            
            // Skip loading state or initial empty state if any
            var item = awaitItem()
            while (item.data == null) {
                item = awaitItem()
            }
            
            assertEquals(categories, item.data)
        }
    }

    @Test
    fun `addCategory should call use case`() = runTest {
        val category = Category(id = "new")
        coEvery { addCategoryUseCase(category) } returns Unit

        viewModel.addCategory(category)
        advanceUntilIdle()

        coVerify { addCategoryUseCase(category) }
    }

    @Test
    fun `deleteCategory should call use case`() = runTest {
        val id = "123"
        coEvery { deleteCategoryUseCase(id) } returns Unit

        viewModel.deleteCategory(id)
        advanceUntilIdle()

        coVerify { deleteCategoryUseCase(id) }
    }
    
    @Test
    fun `syncCategoryToServer should call use case`() = runTest {
        val tag = "sync_tag"
        every { syncCategoryToServerUseCase(tag) } returns Unit

        viewModel.syncCategoryToServer(tag)
        advanceUntilIdle()

        coVerify { syncCategoryToServerUseCase(tag) }
    }

    @Test
    fun `rejectCategoryChanges should call syncCategoryFromServerUseCase with isForce true`() = runTest {
        coEvery { syncCategoryFromServerUseCase(isForce = true) } returns Result.success(Unit)

        viewModel.rejectCategoryChanges()
        advanceUntilIdle()

        coVerify { syncCategoryFromServerUseCase(isForce = true) }
    }
}
