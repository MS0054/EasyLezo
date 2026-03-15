package com.appricut.easylezo.core.domain.usecase.appLanguages

import android.util.Log
import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.model.Language
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.usecase.language.GetLanguagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetAppLanguagesUseCase @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val appLanguagesRepository: AppLanguagesRepository
) {

    operator fun invoke(): Flow<AppLanguages> {

        val appLanguagesFlow =  appLanguagesRepository.observeAppLanguages()
        val languagesFlow = getLanguagesUseCase()

        return combine(languagesFlow, appLanguagesFlow) { languageList, appLang ->
            val fromLanguages = languageList.filter { it.isFromLanguage }
            val toLanguages = languageList.filter { it.isToLanguage }

            val fromLanguage =
                languageList.find { it.name == appLang.from } ?: fromLanguages.firstOrNull()
            val toLanguage =
                languageList.find { it.name == appLang.to } ?: toLanguages.firstOrNull()

            appLang.copy(
                fromLanguages = fromLanguages,
                toLanguages = toLanguages,
                fromLanguage = fromLanguage ?: Language(),
                toLanguage = toLanguage ?: Language()
            )
        }.distinctUntilChanged()
    }




}