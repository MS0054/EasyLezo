package am.mojtaba.armengo.core.data.remote.api

import am.mojtaba.armengo.core.data.remote.model.AppLanguagesDto
import am.mojtaba.armengo.core.data.remote.model.LastUpdateDto
import am.mojtaba.armengo.core.data.remote.model.MetadataDto
import am.mojtaba.armengo.core.data.remote.model.SentenceDto
import am.mojtaba.armengo.core.data.remote.model.SettingsDto
import am.mojtaba.armengo.core.domain.model.AppLanguages
import am.mojtaba.armengo.core.domain.model.LastUpdate
import am.mojtaba.armengo.core.domain.model.ResourceDto
import am.mojtaba.armengo.core.domain.model.Settings
import am.mojtaba.armengo.core.domain.model.Translate
import am.mojtaba.armengo.core.domain.model.UpdateInfoDto
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

    override suspend fun updateMetadataLastUpdate(lastUpdate: LastUpdateDto) {
        try {
            metadataCol.document("Main").update("lastUpdate", lastUpdate).await()
        } catch (e: Exception) {
            // مدیریت خطا (مثلاً عدم دسترسی یا قطعی اینترنت)
            throw e
        }
    }

    override suspend fun updateMetadataAppLanguages(appLanguages: AppLanguagesDto) {
        try {
            metadataCol.document("Main").update("appLanguages", appLanguages).await()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateMetadataSettings(settings: SettingsDto) {
        try {
            metadataCol.document("Main").update("settings", settings).await()
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun updateMetadataUpdateInfo(updateInfo: UpdateInfoDto) {
        try {
            metadataCol.document("Main").update("updateInfo", updateInfo).await()
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun updateMetadataResources(resources: List<ResourceDto>) {
        try {
            metadataCol.document("Main").update("resources", resources).await()
        } catch (e: Exception) {
        }
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

        val metadata = MetadataDto(
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
                id = 1L
            ),
            resources = emptyList(),
            appLanguages = AppLanguagesDto(
                from = "English",
                to ="Persian",
                app = "English"
            ),

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

