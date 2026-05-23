package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ResourceConverter {
    @TypeConverter
    fun fromResourceList(value: List<Resource>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toResourceList(value: String): List<Resource> {
        val listType = object : TypeToken<List<Resource>>() {}.type
        return Gson().fromJson(value, listType)
    }
}