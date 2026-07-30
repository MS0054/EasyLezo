package am.mojtaba.armengo.core.domain.model

import com.google.firebase.firestore.Exclude

data class CategoryWord(
    val id: String = "",
    val categoryId: String = "",
    val wordId: String = "",
    val order: Int = 0,
    @Exclude
    val isSynced: Boolean = true,
    @Exclude
    val isDeleted: Boolean = false,
)
