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

class AddCategoryUseCaseTest {

    // ۱. ساخت یک ماکت (Mock) از ریپازیتوری. به این می‌گویند وابستگی فیک
    @MockK
    private lateinit var categoryRepository: CategoryRepository
    // ۲. تعریف خودِ یوزکیس واقعی که می‌خواهیم تستش کنیم (به این می‌گویند SUT یا همان تحت تست)
    private lateinit var addCategoryUseCase: AddCategoryUseCase

    // این متد قبل از اجرای تک‌تک تست‌ها اجرا می‌شود تا همه چیز از نو ساخته شود
    @Before
    fun setUp() {
        // فعال کردن آنوتیشن‌های MockK
        MockKAnnotations.init(this)
        // ساخت یوزکیس واقعی و تزریق ریپازیتوری فیک به آن
        addCategoryUseCase = AddCategoryUseCase(categoryRepository)
    }

    // این متد بعد از پایان هر تست اجرا می‌شود تا حافظه پاک‌سازی شود
    @After
    fun tearDown() {
        unmockkAll()
    }

    // نوشتن خودِ تست. نام متد تست باید کاملاً توضیح دهد که چه کاری انجام می‌شود
    @Test
    fun `invoke should call addCategoryLocal in repository with correct category`() = runTest {
        // [Stage 1: Given] - چیدن قطعات تست
        // یک کتگوری فرضی و فیک می‌سازیم
        val fakeCategory = Category(
            id = "123",
            order = 1,
            translations = emptyList(),
            fromText = "Hello",
            toText = "Բարև"
        )

        // چون متد ریپازیتوری suspend است، از coEvery استفاده می‌کنیم.
        // به ریپازیتوری فیک می‌گوییم: «اگر متد addCategoryLocal با این کتگوری فرضی صدا زده شد، هیچ کار خاصی نکن و فقط عبور کن»
        coEvery { categoryRepository.addCategoryLocal(fakeCategory) } returns Unit

        // [Stage 2: When] - اجرای عملیات واقعی
        // یوزکیس را صدا می‌زنیم و دیتای فرضی را به آن پاس می‌دهیم
        addCategoryUseCase(fakeCategory)

        // [Stage 3: Then] - بررسی نتیجه
        // چون این یوزکیس خروجی (Result یا دیتا) ندارد، چطور بفهمیم درست کار کرده؟
        // بررسی می‌کنیم که آیا یوزکیس واقعاً متد addCategoryLocal ریپازیتوری را با همین دیتای دقیق صدا زده است یا خیر؟
        coVerify(exactly = 1) { categoryRepository.addCategoryLocal(fakeCategory) }
    }
}