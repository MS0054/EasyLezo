package am.mojtaba.armengo.core.domain.model

import com.google.firebase.firestore.Exclude

data class CategorySentence(
    val id: String = "",
    val categoryId: String = "",
    val sentenceId: String = "",
    val order: Int = 0,
    @Exclude
    val isSynced: Boolean = true,
    @Exclude
    val isDeleted: Boolean = false,
)
