package am.mojtaba.armengo.core.data.remote.model

import com.google.firebase.firestore.Exclude

data class CategoryWordDto(
    val id: String = "",
    val categoryId: String = "",
    val wordId: String = "",
    val order: Int = 0,
    @Exclude
    val isSynced: Boolean = true,
    @Exclude
    val isDeleted: Boolean = false,
)
