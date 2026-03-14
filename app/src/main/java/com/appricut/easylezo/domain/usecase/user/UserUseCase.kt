package com.appricut.easylezo.domain.usecase.user

import com.appricut.easylezo.data.repository.CategoryRepository2
import com.appricut.easylezo.data.repository.SentenceRepository2
import com.appricut.easylezo.domain.model.Category
import com.appricut.easylezo.domain.model.Sentence
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val categoryRepo: CategoryRepository2,
    private val sentenceRepo: SentenceRepository2
) {

    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()
    suspend fun fetchSentencesForCategory(categoryId: String): List<Sentence> = sentenceRepo.fetchSentences(categoryId)
}