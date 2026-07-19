package am.mojtaba.armengo.ui.manager
import am.mojtaba.armengo.app.R
import android.content.Context
import am.mojtaba.armengo.core.domain.model.AppError
import am.mojtaba.armengo.core.domain.usecase.error.GetTranslatedErrorTextUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMessageProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getTranslatedErrorTextUseCase: GetTranslatedErrorTextUseCase
) {
    suspend fun getMessage(throwable: Throwable): String {
        // ۱. پیدا کردن کد/کلید خطا بر اساس نوع Exception
        val errorCode = when (throwable) {
            is AppError.Network -> throwable.errorKey
            is AppError.Server -> throwable.errorKey
            is AppError.Local -> throwable.errorKey
            else -> "err_unknown"
        }

        return try {
            val translatedText = getTranslatedErrorTextUseCase(errorCode)

            if (translatedText == "Unknown Error" || translatedText == "Failed to fetch error text") {
                getFallbackMessage(throwable)
            } else {
                translatedText
            }
        } catch (e: Exception) {
            getFallbackMessage(throwable)
        }
    }

    private fun getFallbackMessage(throwable: Throwable): String {
        return when (throwable) {
            is AppError.Network -> context.getString(R.string.err_network_unavailable)
            is AppError.Local -> context.getString(R.string.err_local_data_empty)
            else -> context.getString(R.string.err_unknown)
        }
    }
}