package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.ReportStatusType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReportStatusTypeConverter {
    @TypeConverter
    fun fromReportStatusTypeList(value: List<ReportStatusType>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toReportStatusTypeList(value: String): List<ReportStatusType> {
        val listType = object : TypeToken<List<ReportStatusType>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
