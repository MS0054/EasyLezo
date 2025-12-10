package com.appricut.easylezo.domain.usecase

import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence
import com.appricut.easylezo.data.repo.AdminRepository
import com.appricut.easylezo.data.repo.AdminRepository_Factory
import com.appricut.easylezo.data.repo.AuthRepository
import com.appricut.easylezo.data.repo.CategoryRepository
import com.appricut.easylezo.data.repo.SentenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminUseCase @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val sentenceRepo: SentenceRepository
) {

    // --- Categories ---
    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()
    suspend fun addCategory(cat: Category): String = categoryRepo.addCategory(cat)
    suspend fun updateCategory(cat: Category) = categoryRepo.updateCategory(cat)
    suspend fun deleteCategory(categoryId: String) = categoryRepo.deleteCategory(categoryId)

    // --- Sentences ---
    suspend fun fetchSentences(categoryId: String): List<Sentence> = sentenceRepo.fetchSentences(categoryId)
    suspend fun addSentence(categoryId: String, s: Sentence): String = sentenceRepo.addSentence(categoryId, s)
    suspend fun updateSentence(categoryId: String, s: Sentence) = sentenceRepo.updateSentence(categoryId, s)
    suspend fun deleteSentence(categoryId: String, sentenceId: String) = sentenceRepo.deleteSentence(categoryId, sentenceId)


}
