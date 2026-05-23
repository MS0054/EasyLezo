package am.mojtaba.armengo.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import am.mojtaba.armengo.core.data.local.dao.AppLanguagesDao
import am.mojtaba.armengo.core.data.local.dao.CategoryDao
import am.mojtaba.armengo.core.data.local.dao.LanguageDao
import am.mojtaba.armengo.core.data.local.dao.MetadataDao
import am.mojtaba.armengo.core.data.local.dao.SentenceDao
import am.mojtaba.armengo.core.data.local.dao.UserDao
import am.mojtaba.armengo.core.data.local.entity.AppLanguagesEntity
import am.mojtaba.armengo.core.data.local.entity.CategoryEntity
import am.mojtaba.armengo.core.data.local.entity.LanguageEntity
import am.mojtaba.armengo.core.data.local.entity.MetadataEntity
import am.mojtaba.armengo.core.data.local.entity.SentenceEntity
import am.mojtaba.armengo.core.data.local.entity.UserEntity
import am.mojtaba.armengo.core.data.local.entity.converter.ResourceConverter
import am.mojtaba.armengo.core.data.local.entity.converter.TranslateConverter

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
