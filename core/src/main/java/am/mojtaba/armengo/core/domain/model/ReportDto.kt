package am.mojtaba.armengo.core.domain.model

import com.google.firebase.firestore.PropertyName

data class ReportDto(
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",
    @get:PropertyName("reportType") @set:PropertyName("reportType") var reportType: String = "",
    @get:PropertyName("reportMessageKey") @set:PropertyName("reportMessageKey") var reportMessageKey: String = "",
    @get:PropertyName("reportStatusType") @set:PropertyName("reportStatusType") var reportStatusType: ReportStatusType = ReportStatusType(),
    @get:PropertyName("item_id") @set:PropertyName("item_id") var itemId: String = "",
    @get:PropertyName("user_comment") @set:PropertyName("user_comment") var userComment: String = "",
    @get:PropertyName("app_version") @set:PropertyName("app_version") var appVersion: String = "",
    @get:PropertyName("device_info") @set:PropertyName("device_info") var deviceInfo: String = "",
    @get:PropertyName("created_at") @set:PropertyName("created_at") var createdAt: Long = System.currentTimeMillis(),
    @get:PropertyName("user_appLanguage") @set:PropertyName("user_appLanguage") var userAppLanguage: String = ""
)
