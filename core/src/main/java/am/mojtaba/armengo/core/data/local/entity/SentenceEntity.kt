package am.mojtaba.armengo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import am.mojtaba.armengo.core.domain.model.Translate

@Entity(tableName = "sentence")
data class SentenceEntity(
    @PrimaryKey
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList()
)
