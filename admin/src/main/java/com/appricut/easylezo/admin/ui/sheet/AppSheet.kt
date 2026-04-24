package com.appricut.easylezo.admin.ui.sheet

import com.appricut.easylezo.core.domain.model.Category
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.model.Resource
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.model.User

sealed class AppSheet {
    object None : AppSheet()

    object LogoutConfirm : AppSheet()

    object LastUpdate : AppSheet()
    object AppLanguage : AppSheet()
    object Settings: AppSheet()
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

