package com.appricut.easylezo.core.data.remote.api

import android.os.Environment
import com.appricut.easylezo.core.data.remote.model.SentenceDto
import com.google.firebase.firestore.FirebaseFirestore
import io.github.whitemagic2014.tts.TTS
import io.github.whitemagic2014.tts.TTSVoice
import io.github.whitemagic2014.tts.bean.Voice
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SentenceApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : SentenceApi {

    private val sentencesCol = db.collection("Sentences")

    override suspend fun getSentences(): List<SentenceDto> {
        val snap = sentencesCol.get().await()
        return snap.documents.mapNotNull { doc ->
            doc.toObject(SentenceDto::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun addSentence(sentence: SentenceDto) {
        try {
            sentencesCol.add(sentence).await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun updateSentence(sentence: SentenceDto) {
        try {
            sentencesCol.document(sentence.id).set(sentence).await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun deleteSentence(sentence: SentenceDto) {
        try {
            sentencesCol.document(sentence.id).delete().await()
        } catch (e: Exception) {
            // manage network error
            throw e
        }
    }

    override suspend fun sortSentences(sentences: List<SentenceDto>) {
        val batch = db.batch()

        sentences.forEach { sentence ->
            batch.update(
                sentencesCol.document(sentence.id),
                "order",
                sentence.order
            )
        }
    }

    override suspend fun downloadVoices(sentences: List<SentenceDto>) {
        val saveDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "word")
        val speakerType = "en-US-AvaNeural"
        val text = "hello"
        val fileCode = "5616132941561"
//
//        val speakerType = "hy-AM-AnahitNeural"
//        val text = "բարի լույս"
//        val fileCode = "5616132941564"



        val voice: Voice = TTSVoice.provides().stream().filter { v: Voice ->
            v.shortName == speakerType
        }.collect(Collectors.toList())[0]
        val fileName = TTS(voice, text)
            .findHeadHook()
            .isRateLimited(true)
            .fileName(fileCode) // You can customize the file name; if omitted, a random file name will be generated.
            .overwrite(false) // When the specified file name is the same, it will either overwrite or append to the file.
            .formatMp3() // default mp3.
            //                .formatOpus() // or opus
            //                .voicePitch()
            //                .voiceRate()
            //                .voiceVolume()
            .storage(saveDirectory.absolutePath)  // the output file storage ,default is ./storage
            //                .connectTimeout(0) // set connect timeout
            .trans()
    }


//    override suspend fun downloadVoices(sentences: List<SentenceDto>) {
//        val saveDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "word")
//
//        // لیستی از جملات را پیمایش کنید (چون ورودی شما لیست است)
//        sentences.forEach { sentence ->
//            // تشخیص زبان یا تعیین گوینده به صورت شرطی
//            // اگر متن ارمنی است از "hy-AM-AnahitNeural" و اگر انگلیسی است از "en-US-AvaNeural" استفاده کنید
//            val isArmenian = sentence.text.any { it in '\u0530'..'\u058F' } // بررسی بازه کاراکترهای ارمنی
//            val speakerType = if (isArmenian) "hy-AM-AnahitNeural" else "en-US-AvaNeural"
//
//            val text = sentence.text
//            val fileCode = sentence.id // یا هر کد یکتایی که دارید
//
//            try {
//                val voice: Voice = TTSVoice.provides().stream().filter { v: Voice ->
//                    v.shortName == speakerType
//                }.findFirst().orElse(null) ?: return@forEach
//
//                TTS(voice, text)
//                    .findHeadHook()
//                    .isRateLimited(true)
//                    .fileName(fileCode)
//                    .overwrite(false)
//                    .formatMp3()
//                    .storage(saveDirectory.absolutePath)
//                    .trans()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
}
