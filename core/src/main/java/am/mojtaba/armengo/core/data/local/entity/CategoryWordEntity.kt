package am.mojtaba.armengo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.UUID

@Entity(
    tableName = "category_word",
    indices = [
        Index(value = ["categoryId"]),
        Index(value = ["wordId"]),
        Index(value = ["categoryId", "wordId"], unique = true)
    ]
)
data class CategoryWordEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val categoryId: String,
    val wordId: String,
    val order: Int = 0,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false
)
