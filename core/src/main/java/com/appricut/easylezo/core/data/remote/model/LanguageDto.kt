package com.appricut.easylezo.core.data.remote.model

import com.google.firebase.firestore.PropertyName

data class LanguageDto(
    val id: String = "",
    val name: String = "",
    val code: String = "",
    val flag: String = "",
//    annotation for prevent conflict with firebase field name
    @get:PropertyName("isFromLanguage")
    val isFromLanguage: Boolean = false,
    @get:PropertyName("isToLanguage")
    val isToLanguage: Boolean = false,
    val order: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)