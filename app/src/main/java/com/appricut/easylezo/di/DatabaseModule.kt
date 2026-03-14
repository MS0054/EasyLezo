package com.appricut.easylezo.di

import android.content.Context
import androidx.room.Room
import com.appricut.easylezo.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.data.local.dao.CategoryDao
import com.appricut.easylezo.data.local.dao.LanguageDao
import com.appricut.easylezo.data.local.dao.MetadataDao
import com.appricut.easylezo.data.local.dao.SentenceDao
import com.appricut.easylezo.data.local.dao.UserDao
import com.appricut.easylezo.data.local.database.AppDatabase
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