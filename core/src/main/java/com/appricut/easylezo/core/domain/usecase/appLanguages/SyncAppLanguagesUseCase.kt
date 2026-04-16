package com.appricut.easylezo.core.domain.usecase.appLanguages

import com.appricut.easylezo.core.domain.model.AppLanguages
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.UserRepository
import javax.inject.Inject

class SyncAppLanguagesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val appLanguagesRepository: AppLanguagesRepository
) {
    suspend operator fun invoke(appLanguages: AppLanguages?, newAppLanguage: AppLanguages) {

        // check if field from of appLanguage equals to to field of newAppLanguage change the to field of newAppLanguage to previous value of from field on appLanguage
        if (newAppLanguage.from == appLanguages?.to) {
            if (appLanguages.toLanguages.contains(appLanguages.fromLanguage)) {
                newAppLanguage.to = appLanguages.from
            }else{
                newAppLanguage.to = "English"
            }
        }
        if (newAppLanguage.to == appLanguages?.from) {
            if (appLanguages.fromLanguages.contains(appLanguages.toLanguage)) {
                newAppLanguage.from = appLanguages.to
            }else{
                newAppLanguage.from = "English"
            }
        }


        appLanguagesRepository.updateLocalAppLanguages(newAppLanguage)
        // ۲. تلاش برای آپدیت سرور
        try {
            userRepository.updateUserAppLanguagesServer(newAppLanguage)
        } catch (e: Exception) {
            // اگر آفلاین بود، اینجا مدیریت می‌شود (مثلاً با WorkManager برای بعدا)
        }
    }



}