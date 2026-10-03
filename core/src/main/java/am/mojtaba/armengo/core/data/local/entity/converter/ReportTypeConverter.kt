package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.ReportType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReportTypeConverter {
    @TypeConverter
    fun fromReportTypeList(value: List<ReportType>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toReportTypeList(value: String): List<ReportType> {
        val listType = object : TypeToken<List<ReportType>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
