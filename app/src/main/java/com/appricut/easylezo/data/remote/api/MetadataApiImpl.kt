package com.appricut.easylezo.data.remote.api

import com.appricut.easylezo.data.local.entity.AppLanguagesEntity
import com.appricut.easylezo.data.remote.model.MetadataDto
import com.appricut.easylezo.data.remote.model.SentenceDto
import com.appricut.easylezo.domain.model.AppLanguages
import com.appricut.easylezo.domain.model.LastUpdate
import com.appricut.easylezo.domain.model.Metadata
import com.appricut.easylezo.domain.model.Resource
import com.appricut.easylezo.domain.model.Sentence
import com.appricut.easylezo.domain.model.Settings
import com.appricut.easylezo.domain.model.Translate
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MetadataApiImpl @Inject constructor(
    private val db: FirebaseFirestore
) : MetadataApi {


    private val metadataCol = db.collection("Metadata")

    override suspend fun getMetadata(): MetadataDto {
//        dothis3()
//        dothis()
//        dothis2()

        val main = metadataCol.document("Main").get().await()
        return main.toObject(MetadataDto::class.java) ?: MetadataDto()

//        return MetadataDto(
//            id = firestoreDto.id,
//            lastUpdate = firestoreDto.lastUpdate,
//            settings = firestoreDto.settings,
//            resource = firestoreDto.resource
//        )
    }

    suspend fun dothis(){
                val userLanguages2 = AppLanguages(

                    from = "English",
                    to ="Persian",
                    app = "English"
                )
        db.collection("Metadata").document("Settings")
            .update(
                mapOf(
                    "appLanguages" to userLanguages2
                )
            ).await()
    }

    suspend fun dothis3(){
        // create list of Translate with fake data
        val translations = listOf(
            Translate("English", "Hello"),
            Translate("Persian", "سلام"),
            Translate("Armenian", "Barev"),
            Translate("Russian", "Russian")
        )

        val sen = SentenceDto(
            id = "1",
            categoryId = "CV2oNsuWO2n0ETi7A4Xg",
            level = "1",
            image = "",
            order = 1,
            translations = translations
        )
        db.collection("Sentences").document("f8uaWZvuEXXMIAh5yxwC")
            .set(sen).await()

    }
    suspend fun dothis2(){

        val metadata = Metadata(
            id = 1L,
            lastUpdate = LastUpdate(
                category = 7,
                language = 8,
                sentence = 9,
                user = 10,
                existNewUserData=true,
                existNewLanguageData=true,
                existNewCategoryData=true,
                existNewSentenceData=true
            ),
            settings = Settings(
                id = 1L,
                appLanguages = AppLanguagesEntity(

                    from = "English",
                    to ="Persian",
                    app = "English"
                )
            ),
        resource = Resource(

        )
        )


        db.collection("Metadata").document("Main")
            .set(metadata).await()
    }


//    override suspend fun getAppLanguages(): AppLanguages {
//        val settings = metadataCol.document("Settings").get().await()
//        val appLanguages = settings.get("appLanguages") as AppLanguages
//        return appLanguages
//    }

}

