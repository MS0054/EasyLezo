package com.appricut.easylezo.data.repo

import com.appricut.easylezo.data.model.Category
import com.appricut.easylezo.data.model.Sentence
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(
    private val categoryRepo: CategoryRepository,
    private val sentenceRepo: SentenceRepository
) {
    // Category CRUD
    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()
    suspend fun addCategory(cat: Category): String = categoryRepo.addCategory(cat)
    suspend fun updateCategory(cat: Category) = categoryRepo.updateCategory(cat)
    suspend fun deleteCategory(catId: String) = categoryRepo.deleteCategory(catId)

    // Sentence CRUD
    suspend fun fetchSentences(categoryId: String): List<Sentence> = sentenceRepo.fetchSentences(categoryId)
    suspend fun addSentence(categoryId: String, s: Sentence): String = sentenceRepo.addSentence(categoryId, s)
    suspend fun updateSentence(categoryId: String, s: Sentence) = sentenceRepo.updateSentence(categoryId, s)
    suspend fun deleteSentence(categoryId: String, sentenceId: String) = sentenceRepo.deleteSentence(categoryId, sentenceId)
}
