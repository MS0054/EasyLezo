package com.appricut.easylezo.core.domain.usecase.user

import com.appricut.easylezo.core.data.repository.CategoryRepository2

import com.appricut.easylezo.core.domain.model.Category

import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val categoryRepo: CategoryRepository2,
) {

    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()
//    suspend fun fetchSentencesForCategory(categoryId: String): List<Sentence> = sentenceRepo.fetchSentences(categoryId)
}