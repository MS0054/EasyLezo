package am.mojtaba.armengo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.UUID

@Entity(
    tableName = "category_sentence",
    indices = [
        Index(value = ["categoryId"]),
        Index(value = ["sentenceId"]),
        // برای جلوگیری از ثبت تکراری یک رابطه یکسان در دیتابیس محلی
        Index(value = ["categoryId", "sentenceId"], unique = true)
    ]
)
data class CategorySentenceEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val categoryId: String,
    val sentenceId: String,
    val order: Int = 0,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false
)
