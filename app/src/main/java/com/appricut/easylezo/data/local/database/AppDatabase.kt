package com.appricut.easylezo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appricut.easylezo.data.local.dao.AppLanguagesDao
import com.appricut.easylezo.data.local.dao.CategoryDao
import com.appricut.easylezo.data.local.dao.LanguageDao
import com.appricut.easylezo.data.local.dao.MetadataDao
import com.appricut.easylezo.data.local.dao.SentenceDao
import com.appricut.easylezo.data.local.dao.UserDao
import com.appricut.easylezo.data.local.entity.AppLanguagesEntity
import com.appricut.easylezo.data.local.entity.CategoryEntity
import com.appricut.easylezo.data.local.entity.LanguageEntity
import com.appricut.easylezo.data.local.entity.MetadataEntity
import com.appricut.easylezo.data.local.entity.SentenceEntity
import com.appricut.easylezo.data.local.entity.UserEntity
import com.appricut.easylezo.data.local.entity.converter.TranslateConverter

@Database(
    entities = [LanguageEntity::class, MetadataEntity::class, UserEntity::class, AppLanguagesEntity::class, CategoryEntity::class, SentenceEntity::class],
    version = 1
)
@TypeConverters(TranslateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDao
    abstract fun metadataDao(): MetadataDao
    abstract fun userDao(): UserDao
    abstract fun appLanguagesDao(): AppLanguagesDao
    abstract fun categoryDao(): CategoryDao
    abstract fun sentenceDao(): SentenceDao
}
