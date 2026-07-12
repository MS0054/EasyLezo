package am.mojtaba.armengo.core.data.local.entity.converter

import am.mojtaba.armengo.core.domain.model.Error
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ErrorConverter {
    @TypeConverter
    fun fromErrorList(value: List<Error>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toErrorList(value: String): List<Error> {
        val listType = object : TypeToken<List<Error>>() {}.type
        return Gson().fromJson(value, listType)
    }
}