package am.mojtaba.armengo.core.domain.model

data class User(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val role: String = "",
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val appLanguages: AppLanguages = AppLanguages(),

)