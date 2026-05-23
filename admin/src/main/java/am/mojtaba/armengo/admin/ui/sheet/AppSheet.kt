package am.mojtaba.armengo.admin.ui.sheet

import am.mojtaba.armengo.core.domain.model.Category
import am.mojtaba.armengo.core.domain.model.Language
import am.mojtaba.armengo.core.domain.model.Resource
import am.mojtaba.armengo.core.domain.model.Sentence
import am.mojtaba.armengo.core.domain.model.User

sealed class AppSheet {
    object None : AppSheet()

    object LogoutConfirm : AppSheet()

    object LastUpdate : AppSheet()
    object AppLanguage : AppSheet()
    object Settings: AppSheet()
    object UpdateInfo: AppSheet()
    object Sync: AppSheet()
    object AddResource: AppSheet()
    class EditResource(val resource: Resource): AppSheet()


    class AddCategory(val maxOrder: Int) : AppSheet()
    object SortCategory : AppSheet()
    class EditCategory(val category: Category) : AppSheet()

    class AddSentence(val maxOrder: Int) : AppSheet()
    object SortSentence : AppSheet()
    class EditSentence(val sentence: Sentence) : AppSheet()

    class AddLanguage(val maxOrder: Int) : AppSheet()
    object SortLanguage : AppSheet()
    class EditLanguage(val language: Language) : AppSheet()

    class EditUser(val user: User) : AppSheet()
}

