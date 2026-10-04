package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.domain.model.ReportDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ReportApi {

    companion object {
        private const val COLLECTION = "Report"
    }

    private val reportCol = db.collection(COLLECTION)

    override suspend fun getReports(): List<ReportDto> {
        val snap = reportCol.get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(ReportDto::class.java)?.apply { id = doc.id }
        }
    }

    override suspend fun addReport(report: ReportDto) {
        val docRef = if (report.id.isNotBlank()) reportCol.document(report.id) else reportCol.document()
        report.id = docRef.id
        docRef.set(report).await()
    }

    override suspend fun deleteReport(reportId: String) {
        reportCol.document(reportId).delete().await()
    }
}
