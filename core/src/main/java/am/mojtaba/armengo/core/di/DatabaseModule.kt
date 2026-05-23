package am.mojtaba.armengo.core.di

import android.content.Context
import androidx.room.Room
import am.mojtaba.armengo.core.data.local.dao.AppLanguagesDao
import am.mojtaba.armengo.core.data.local.dao.CategoryDao
import am.mojtaba.armengo.core.data.local.dao.LanguageDao
import am.mojtaba.armengo.core.data.local.dao.MetadataDao
import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.local.dao.UserDao
import am.mojtaba.armengo.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "lezo_language_app").build()
    @Singleton
    @Provides
    fun provideLanguageDao(db: AppDatabase): LanguageDao = db.languageDao()
    @Singleton
    @Provides
    fun provideMetadataDao(db: AppDatabase): MetadataDao =db.metadataDao()
    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
    @Singleton
    @Provides
    fun provideAppLanguagesDao(db: AppDatabase): AppLanguagesDao = db.appLanguagesDao()
    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
    @Singleton
    @Provides
    fun provideSentenceDao(db: AppDatabase): SentenceDao = db.sentenceDao()
}