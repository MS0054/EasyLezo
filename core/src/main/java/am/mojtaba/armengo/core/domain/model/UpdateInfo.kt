package am.mojtaba.armengo.core.domain.model

data class UpdateInfo(
    val latestVersion: String = "",
    val latestVersionCode: Int = 0,
    val minVersion: String = "",
    val minVersionCode: Int = 0,
    val forcedVersions: String = "",
    val updateUrl: String = "",
    val releaseNotes: String = ""
)

enum class UpdateType {
    FORCE, OPTIONAL, NONE
}