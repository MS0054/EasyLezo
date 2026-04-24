package com.appricut.easylezo.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appricut.easylezo.core.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.core.data.local.dao.CategoryDao
import com.appricut.easylezo.core.data.local.dao.LanguageDao
import com.appricut.easylezo.core.data.local.dao.MetadataDao
import com.appricut.easylezo.core.data.local.dao.SentenceDao
import com.appricut.easylezo.core.data.local.dao.UserDao
import com.appricut.easylezo.core.data.local.entity.AppLanguagesEntity
import com.appricut.easylezo.core.data.local.entity.CategoryEntity
import com.appricut.easylezo.core.data.local.entity.LanguageEntity
import com.appricut.easylezo.core.data.local.entity.MetadataEntity
import com.appricut.easylezo.core.data.local.entity.SentenceEntity
import com.appricut.easylezo.core.data.local.entity.UserEntity
import com.appricut.easylezo.core.data.local.entity.converter.ResourceConverter
import com.appricut.easylezo.core.data.local.entity.converter.TranslateConverter

@Database(
    entities = [LanguageEntity::class, MetadataEntity::class, UserEntity::class, AppLanguagesEntity::class, CategoryEntity::class, SentenceEntity::class],
    version = 1
)
@TypeConverters(TranslateConverter::class, ResourceConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDao
    abstract fun metadataDao(): MetadataDao
    abstract fun userDao(): UserDao
    abstract fun appLanguagesDao(): AppLanguagesDao
    abstract fun categoryDao(): CategoryDao
    abstract fun sentenceDao(): SentenceDao
}
