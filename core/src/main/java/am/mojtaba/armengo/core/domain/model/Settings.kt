package am.mojtaba.armengo.core.domain.model


data class Settings(
    val id: Long = 0L,
    val lastVersion: String = "",
    val policyUrl: String = "",
    val aboutUrl : String = "",
    val termsUrl : String = ""
)