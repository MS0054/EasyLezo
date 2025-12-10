package com.appricut.easylezo.domain.usecase

import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence
import com.appricut.easylezo.data.repo.CategoryRepository
import com.appricut.easylezo.data.repo.SentenceRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val sentenceRepo: SentenceRepository
) {

    // --- Categories ---
    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()

    // --- Sentences ---
    suspend fun fetchSentencesForCategory(categoryId: String): List<Sentence> =
        sentenceRepo.fetchSentences(categoryId)
}
