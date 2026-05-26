package am.mojtaba.armengo.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val code: String = "",
    val flag: String = "",
    val isFromLanguage: Boolean = false,
    val isToLanguage: Boolean = false,
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isSynced: Boolean = true,
    val isDeleted: Boolean = false,
)