package am.mojtaba.armengo.admin.ui.sheet

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.model.Error
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Report
import am.mojtaba.armengo.core.domain.model.ReportMessage
import am.mojtaba.armengo.core.domain.model.ReportType
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.User
import am.mojtaba.armengo.core.domain.model.Word

sealed class AppSheet {
    object None : AppSheet()
    object Logout : AppSheet()
    object LastUpdate : AppSheet()
    object AppLanguage : AppSheet()
    object Settings: AppSheet()
    object UpdateInfo: AppSheet()
    object Sync: AppSheet()

    object AddResource: AppSheet()
    class EditResource(val resource: Resource): AppSheet()

    object AddReportType: AppSheet()
    class EditReportType(val reportType: ReportType): AppSheet()

    object AddReport: AppSheet()
    class EditReport(val report: Report): AppSheet()
    class ShowReport(val report: Report): AppSheet()

    object AddReportMessage: AppSheet()
    class EditReportMessage(val reportMessage: ReportMessage): AppSheet()

    object AddError: AppSheet()
    class EditError(val error: Error): AppSheet()

    class AddCategory(val maxOrder: Int) : AppSheet()
    object SortCategory : AppSheet()
    class EditCategory(val category: Category) : AppSheet()

    object AddSentence : AppSheet()
    class EditSentence(val sentence: Sentence) : AppSheet()
    class ShowReportItem(val sentence: Sentence? = null, val word: Word? = null) : AppSheet()

    object AssignCategorySentence : AppSheet()
    object SortCategorySentence : AppSheet()

    class AddWord(val maxOrder: Int) : AppSheet()
    object SortWord : AppSheet()
    class EditWord(val word: Word) : AppSheet()

    object AssignCategoryWord : AppSheet()
    object SortCategoryWord : AppSheet()

    class AddLanguage(val maxOrder: Int) : AppSheet()
    object SortLanguage : AppSheet()
    class EditLanguage(val language: Language) : AppSheet()

    class EditUser(val user: User) : AppSheet()
}

