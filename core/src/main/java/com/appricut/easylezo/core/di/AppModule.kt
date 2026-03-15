package com.appricut.easylezo.core.di

import android.content.Context
import com.appricut.easylezo.core.data.datastore.AppDataStore
import com.appricut.easylezo.core.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.core.data.local.dao.CategoryDao
import com.appricut.easylezo.core.data.local.dao.LanguageDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.local.dao.SentenceDao
import com.appricut.easylezo.core.data.local.dao.UserDao
import com.appricut.easylezo.core.data.remote.api.AuthApi
import com.appricut.easylezo.core.data.remote.api.AuthApiImpl
import com.appricut.easylezo.core.data.remote.api.CategoryApi
import com.appricut.easylezo.core.data.remote.api.CategoryApiImpl
import com.appricut.easylezo.core.data.remote.api.LanguageApi
import com.appricut.easylezo.core.data.remote.api.LanguageApiImpl
import com.appricut.easylezo.core.data.remote.api.MetadataApi
import com.appricut.easylezo.core.data.remote.api.MetadataApiImpl
import com.appricut.easylezo.core.data.remote.api.SentenceApi
import com.appricut.easylezo.core.data.remote.api.SentenceApiImpl
import com.appricut.easylezo.core.data.remote.api.UserApi
import com.appricut.easylezo.core.data.remote.api.UserApiImpl
import com.appricut.easylezo.core.data.repository.AppLanguagesRepositoryImpl
import com.appricut.easylezo.core.data.repository.AuthRepository2
import com.appricut.easylezo.core.data.repository.AuthRepositoryImpl
import com.appricut.easylezo.core.data.repository.CategoryRepository2
import com.appricut.easylezo.core.data.repository.CategoryRepositoryImpl
import com.appricut.easylezo.core.data.repository.LanguageRepositoryImpl
import com.appricut.easylezo.core.data.repository.MetadataRepositoryImpl
import com.appricut.easylezo.core.data.repository.SentenceRepository2
import com.appricut.easylezo.core.data.repository.SentenceRepositoryImpl
import com.appricut.easylezo.core.data.repository.UserRepositoryImpl
import com.appricut.easylezo.core.domain.repository.AppLanguagesRepository
import com.appricut.easylezo.core.domain.repository.AuthRepository
import com.appricut.easylezo.core.domain.repository.CategoryRepository
import com.appricut.easylezo.core.domain.repository.LanguageRepository
import com.appricut.easylezo.core.domain.repository.MetadataRepository
import com.appricut.easylezo.core.domain.repository.SentenceRepository
import com.appricut.easylezo.core.domain.repository.UserRepository
import com.appricut.easylezo.core.domain.usecase.AdminUseCase
import com.appricut.easylezo.core.domain.usecase.AuthUseCase
import com.appricut.easylezo.core.domain.usecase.category.GetCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.user.UserUseCase
import com.appricut.easylezo.core.domain.usecase.language.GetLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.metadata.GetMetadataUseCase
import com.appricut.easylezo.core.domain.usecase.category.SyncCategoriesUseCase
import com.appricut.easylezo.core.domain.usecase.language.SyncLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.metadata.SyncMetadataUseCase
import com.appricut.easylezo.core.domain.usecase.user.SyncUserUseCase
import com.appricut.easylezo.core.domain.usecase.appLanguages.SyncAppLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.user.DecideUserRoleUseCase
import com.appricut.easylezo.core.domain.usecase.appLanguages.GetAppLanguagesUseCase
import com.appricut.easylezo.core.domain.usecase.sentence.GetSentencesUseCase
import com.appricut.easylezo.core.domain.usecase.sentence.SyncSentencesUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideAppDataStore(context: Context) = AppDataStore(context)



    //--- Repository ---


    @Singleton
    @Provides
    fun provideAuthRepository2(auth: FirebaseAuth, db: FirebaseFirestore) = AuthRepository2(auth, db)

    @Singleton
    @Provides
    fun provideSentenceRepository2(db: FirebaseFirestore) = SentenceRepository2(db)

    @Provides
    @Singleton
    fun provideLanguageRepository(
        metadataRepository: MetadataRepository,
        languageDao: LanguageDao,
        languageApi: LanguageApi,

    ): LanguageRepository {
        return LanguageRepositoryImpl(
            metadataRepository,
            languageDao,
            languageApi
        )
    }

    @Singleton
    @Provides
    fun provideMetadataRepository(
        appLanguagesRepository: AppLanguagesRepository,
        metadataDao: MetadataDao,
        metadataApi: MetadataApi
    ): MetadataRepository {
        return MetadataRepositoryImpl(
            appLanguagesRepository,
            metadataDao,
            metadataApi
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepository = AuthRepositoryImpl(
        authApi
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        metadataRepository: MetadataRepository,
        appLanguagesRepository: AppLanguagesRepository,
        userDao: UserDao,
        userApi: UserApi
    ): UserRepository = UserRepositoryImpl(
        metadataRepository,
        appLanguagesRepository,
        userDao,
        userApi
    )

    @Singleton
    @Provides
    fun provideAppLanguagesRepository(
        appLanguagesDao: AppLanguagesDao
    ): AppLanguagesRepository = AppLanguagesRepositoryImpl(
        appLanguagesDao
    )

    @Singleton
    @Provides
    fun provideCategoryRepository(
        metadataRepository: MetadataRepository,
        categoryDao: CategoryDao,
        categoryApi: CategoryApi
    ): CategoryRepository = CategoryRepositoryImpl(
        metadataRepository,
        categoryDao,
        categoryApi
    )

    @Singleton
    @Provides
    fun provideSentenceRepository(
        metadataRepository: MetadataRepository,
        sentenceDao: SentenceDao,
        sentenceApi: SentenceApi
    ): SentenceRepository = SentenceRepositoryImpl(
        metadataRepository,
        sentenceDao,
        sentenceApi
    )



    //--- Api ---

    @Singleton
    @Provides
    fun provideLanguageApi(db: FirebaseFirestore): LanguageApi = LanguageApiImpl(db)

    @Singleton
    @Provides
    fun provideMetadataApi(db: FirebaseFirestore): MetadataApi = MetadataApiImpl(db)

    @Singleton
    @Provides
    fun provideAuthApi(auth: FirebaseAuth, db: FirebaseFirestore): AuthApi = AuthApiImpl(auth, db)


    @Singleton
    @Provides
    fun provideUserApi(db: FirebaseFirestore): UserApi = UserApiImpl(db)

    @Singleton
    @Provides
    fun provideCategoryApi(db: FirebaseFirestore): CategoryApi = CategoryApiImpl(db)

    @Singleton
    @Provides
    fun provideSentenceApi(db: FirebaseFirestore): SentenceApi = SentenceApiImpl(db)






    // --- UseCases ---
    @Singleton
    @Provides
    fun provideAuthUseCase(authRepo: AuthRepository2) = AuthUseCase(authRepo)

    @Singleton
    @Provides
    fun provideGetLanguagesUseCase(languageRepo: LanguageRepository) = GetLanguagesUseCase(languageRepo)

    @Singleton
    @Provides
    fun provideSyncLanguagesUseCase(languageRepo: LanguageRepository) = SyncLanguagesUseCase(languageRepo)

    @Singleton
    @Provides
    fun provideSyncMetadataUseCase(metadataRepo: MetadataRepository) = SyncMetadataUseCase(metadataRepo)
    @Singleton
    @Provides
    fun provideGetMetadataUseCase(metadataRepo: MetadataRepository) = GetMetadataUseCase(metadataRepo)
    @Singleton
    @Provides
    fun provideDecideUserRoleUseCase(authRepo: AuthRepository) = DecideUserRoleUseCase(authRepo)
    @Singleton
    @Provides
    fun provideUserUseCase(categoryRepo: CategoryRepository2, sentenceRepo: SentenceRepository2) = UserUseCase(categoryRepo, sentenceRepo)
    @Singleton
    @Provides
    fun provideAdminUseCase(categoryRepo: CategoryRepository2, sentenceRepo: SentenceRepository2) = AdminUseCase(categoryRepo, sentenceRepo)
    @Singleton
    @Provides
    fun provideGetAppLanguagesUseCase(getLanguagesUseCase: GetLanguagesUseCase, appLanguagesRepository: AppLanguagesRepository) = GetAppLanguagesUseCase(getLanguagesUseCase,  appLanguagesRepository)
    @Singleton
    @Provides
    fun provideSyncUserUseCase(userRepository: UserRepository, authRepository: AuthRepository) = SyncUserUseCase(userRepository, authRepository)
    @Singleton
    @Provides
    fun provideUpdateUserAppLanguagesUseCase(userRepository: UserRepository, appLanguagesRepository: AppLanguagesRepository) = SyncAppLanguagesUseCase(userRepository, appLanguagesRepository)
    @Singleton
    @Provides
    fun provideSyncCategoriesUseCase(categoryRepository: CategoryRepository)= SyncCategoriesUseCase(categoryRepository)
    @Singleton
    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository)= GetCategoriesUseCase(categoryRepository)
    @Singleton
    @Provides
    fun provideGetSentencesUseCase(sentenceRepository: SentenceRepository, appLanguagesRepository: AppLanguagesRepository) = GetSentencesUseCase(sentenceRepository, appLanguagesRepository)
    @Singleton
    @Provides
    fun provideSyncSentencesUseCase(sentenceRepository: SentenceRepository) = SyncSentencesUseCase(sentenceRepository)

}
