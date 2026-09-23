package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.SentenceAlternative
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SentenceAlternativeConverter {
    @TypeConverter
    fun fromSentenceAlternativeList(value: List<SentenceAlternative>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toSentenceAlternativeList(value: String): List<SentenceAlternative> {
        val listType = object : TypeToken<List<SentenceAlternative>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
