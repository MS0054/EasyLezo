package am.mojtaba.armengo.core.di

import android.content.Context
import am.mojtaba.armengo.core.data.datastore.AppDataStore
import am.mojtaba.armengo.core.data.local.dao.AppLanguagesDao
import am.mojtaba.armengo.core.data.local.dao.CategoryDao
import am.mojtaba.armengo.core.data.local.dao.LanguageDao
import am.mojtaba.armengo.core.data.local.dao.MetadataDao
import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.local.dao.UserDao
import am.mojtaba.armengo.core.data.manager.SyncManagerImpl
import am.mojtaba.armengo.core.data.remote.api.AuthApi
import am.mojtaba.armengo.core.data.remote.api.AuthApiImpl
import am.mojtaba.armengo.core.data.remote.api.CategoryApi
import am.mojtaba.armengo.core.data.remote.api.CategoryApiImpl
import am.mojtaba.armengo.core.data.remote.api.LanguageApi
import am.mojtaba.armengo.core.data.remote.api.LanguageApiImpl
import am.mojtaba.armengo.core.data.remote.api.MetadataApi
import am.mojtaba.armengo.core.data.remote.api.MetadataApiImpl
import am.mojtaba.armengo.core.data.remote.api.SentenceApi
import am.mojtaba.armengo.core.data.remote.api.SentenceApiImpl
import am.mojtaba.armengo.core.data.remote.api.UserApi
import am.mojtaba.armengo.core.data.remote.api.UserApiImpl
import am.mojtaba.armengo.core.data.repository.AppInfoProviderImpl
import am.mojtaba.armengo.core.data.repository.AppLanguagesRepositoryImpl
import am.mojtaba.armengo.core.data.repository.AuthRepository2
import am.mojtaba.armengo.core.data.repository.AuthRepositoryImpl
import am.mojtaba.armengo.core.data.repository.CategoryRepositoryImpl
import am.mojtaba.armengo.core.data.repository.LanguageRepositoryImpl
import am.mojtaba.armengo.core.data.repository.MetadataRepositoryImpl
import am.mojtaba.armengo.core.data.repository.SentenceRepositoryImpl
import am.mojtaba.armengo.core.data.repository.UserRepositoryImpl
import am.mojtaba.armengo.core.domain.manager.SyncManager
import am.mojtaba.armengo.core.domain.repository.AppInfoProvider
import am.mojtaba.armengo.core.domain.repository.AppLanguagesRepository
import am.mojtaba.armengo.core.domain.repository.AuthRepository
import am.mojtaba.armengo.core.domain.repository.CategoryRepository
import am.mojtaba.armengo.core.domain.repository.LanguageRepository
import am.mojtaba.armengo.core.domain.repository.MetadataRepository
import am.mojtaba.armengo.core.domain.repository.SentenceRepository
import am.mojtaba.armengo.core.domain.repository.UserRepository
import am.mojtaba.armengo.core.domain.usecase.auth.AuthUseCase
import am.mojtaba.armengo.core.domain.usecase.category.GetCategoriesUseCase
import am.mojtaba.armengo.core.domain.usecase.language.GetLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.GetMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.category.SyncCategoriesUseCase
import am.mojtaba.armengo.core.domain.usecase.language.SyncLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.metadata.SyncMetadataUseCase
import am.mojtaba.armengo.core.domain.usecase.user.SyncUserUseCase
import am.mojtaba.armengo.core.domain.usecase.appLanguages.SyncAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.user.DecideUserRoleUseCase
import am.mojtaba.armengo.core.domain.usecase.appLanguages.GetAppLanguagesUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.GetSentencesUseCase
import am.mojtaba.armengo.core.domain.usecase.sentence.SyncSentencesUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideAppInfoProvider(@ApplicationContext context: Context): AppInfoProvider = AppInfoProviderImpl(context)



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



//    --- WordManager ---

    @Singleton
    @Provides
    fun provideSyncManager(@ApplicationContext context: Context): SyncManager = SyncManagerImpl(context)





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
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository, appLanguagesRepository: AppLanguagesRepository)= GetCategoriesUseCase(categoryRepository, appLanguagesRepository)
    @Singleton
    @Provides
    fun provideGetSentencesUseCase(sentenceRepository: SentenceRepository, appLanguagesRepository: AppLanguagesRepository) = GetSentencesUseCase(sentenceRepository, appLanguagesRepository)
    @Singleton
    @Provides
    fun provideSyncSentencesUseCase(sentenceRepository: SentenceRepository) = SyncSentencesUseCase(sentenceRepository)

}
