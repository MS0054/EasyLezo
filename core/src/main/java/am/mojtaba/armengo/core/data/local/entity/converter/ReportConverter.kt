package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.Report
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReportConverter {
    @TypeConverter
    fun fromReportList(value: List<Report>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toReportList(value: String): List<Report> {
        val listType = object : TypeToken<List<Report>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
