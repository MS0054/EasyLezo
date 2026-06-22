package am.mojtaba.armengo.core.data.remote.model

import am.mojtaba.armengo.core.domain.model.Translate
import com.google.firebase.firestore.Exclude


data class CategoryDto(
    val id: String = "",
    val image: String = "",
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    @Exclude
    var isSynced: Boolean = false,
    @Exclude
    var isDeleted: Boolean = false,
    val translations: List<Translate> = emptyList()
)
