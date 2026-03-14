package com.appricut.easylezo.domain.model

data class Sentence(
    val id: String = "",
    val categoryId: String = "",
    val level: String = "",
    val image: String = "",
    val ar: String = "",
    val en: String = "",
    val fa: String = "",
    val fromText:String = "",
    val toText: String= "",
    val order: Int = 0,
    val translations: List<Translate> = emptyList()
)
