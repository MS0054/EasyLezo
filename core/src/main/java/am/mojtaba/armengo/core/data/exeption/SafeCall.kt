package am.mojtaba.armengo.core.data.exeption

import am.mojtaba.armengo.core.domain.model.AppError
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: Exception) {
        Result.failure(e.toAppError())
    }
}

// مپ کردن تمام خطاهای خام اندروید و فایربیس به خطای واحد ما
fun Exception.toAppError(): AppError {
    return when (this) {
        is FirebaseFirestoreException -> {
            if (this.code == FirebaseFirestoreException.Code.UNAVAILABLE) {
                AppError.Network()
            } else {
                // اینجا می‌توانید کد خطای فایربیس را به عنوان کلید بفرستید (مثلا permission-denied)
                AppError.Server("err_server_${this.code.name.lowercase()}")
            }
        }
        is IOException -> AppError.Network()
        is AppError -> this // اگر قبلاً مپ شده باشد
        else -> AppError.Unknown()
    }
}