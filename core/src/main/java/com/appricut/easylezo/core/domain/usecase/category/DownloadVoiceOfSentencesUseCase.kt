package com.appricut.easylezo.core.domain.usecase.category

import android.content.Context
import com.appricut.easylezo.core.domain.model.Sentence
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class DownloadVoiceOfSentencesUseCase @Inject constructor(
    private val sentenceRepository: SentenceRepository
) {
    suspend operator fun invoke(sentences: List<Sentence>) {

        try {
            sentenceRepository.downloadVoice(sentences)
        } catch (e: Exception) {
            //  WorkManager
        }
    }

}

