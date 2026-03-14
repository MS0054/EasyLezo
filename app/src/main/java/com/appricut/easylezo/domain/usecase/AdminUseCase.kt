package com.appricut.easylezo.domain.usecase

import com.appricut.easylezo.data.repository.CategoryRepository2
import com.appricut.easylezo.domain.model.Category
import com.appricut.easylezo.domain.model.Sentence
import com.appricut.easylezo.data.repository.SentenceRepository2
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminUseCase @Inject constructor(
    private val categoryRepo: CategoryRepository2,
    private val sentenceRepo: SentenceRepository2
) {

    // --- Categories ---
    suspend fun fetchAllCategories(): List<Category> = categoryRepo.fetchAllCategories()
    suspend fun addCategory(cat: Category): String = categoryRepo.addCategory(cat)
    suspend fun updateCategory(cat: Category) = categoryRepo.updateCategory(cat)
    suspend fun deleteCategory(categoryId: String) = categoryRepo.deleteCategory(categoryId)
    suspend fun updateCategoriesOrder(categories: List<Category>) = categoryRepo.updateCategoriesOrder(categories)

    // --- Sentences ---
    suspend fun fetchSentences(categoryId: String): List<Sentence> = sentenceRepo.fetchSentences(categoryId)
    suspend fun addSentence(categoryId: String, s: Sentence): String = sentenceRepo.addSentence(categoryId, s)
    suspend fun updateSentence(categoryId: String, s: Sentence) = sentenceRepo.updateSentence(categoryId, s)
    suspend fun deleteSentence(categoryId: String, sentenceId: String) = sentenceRepo.deleteSentence(categoryId, sentenceId)
    suspend fun updateSentencesOrder(categoryId: String, sentences: List<Sentence>) = sentenceRepo.updateSentencesOrder(categoryId, sentences)

}
