package am.mojtaba.armengo.core.data.local.entity.converter

import androidx.room.TypeConverter
import am.mojtaba.armengo.core.domain.model.ReportMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReportMessageConverter {
    @TypeConverter
    fun fromReportMessageList(value: List<ReportMessage>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toReportMessageList(value: String): List<ReportMessage> {
        val listType = object : TypeToken<List<ReportMessage>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
