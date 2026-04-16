package com.appricut.easylezo.core.domain.model

data class AppLanguages(
    val id : Long = 0L,
    var from: String = "",
    var to: String = "",
    val fromLanguage: Language? = Language(),
    val toLanguage: Language? = Language(),
    var languages: List<Language> = emptyList(),
    var fromLanguages: List<Language> = emptyList(),
    val toLanguages: List<Language> = emptyList(),
    var app: String = ""
){
    val isDefault: Boolean get() = from.isEmpty() || to.isEmpty()
}
