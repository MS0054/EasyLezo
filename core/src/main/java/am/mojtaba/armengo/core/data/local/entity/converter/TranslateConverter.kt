package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.Translate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TranslateConverter {
    @TypeConverter
    fun fromTranslateList(value: List<Translate>): String {
        return Gson().toJson(value) // یا استفاده از Kotlin Serialization
    }

    @TypeConverter
    fun toTranslateList(value: String): List<Translate> {
        val listType = object : TypeToken<List<Translate>>() {}.type
        return Gson().fromJson(value, listType)
    }
}