package google.yousefi.cryptoapp.data.source.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import google.yousefi.cryptoapp.model.Percentage

/**
 * کلاس PercentageTypeConverter برای تبدیل نوع داده Percentage به و از رشته JSON استفاده می‌شود.
 * این کلاس برای استفاده در Room Database به عنوان یک TypeConverter طراحی شده است.
 *
 * @property gson یک شیء از کتابخانه Gson برای انجام تبدیل‌های JSON به اشیاء و بالعکس.
 * @constructor سازنده کلاس برای مقداردهی اولیه شیء Gson.
 */
class PercentageTypeConverter {
    private val gson = Gson()

    /**
     * تبدیل Percentage به رشته JSON.
     *
     * @param percentage شیء Percentage برای تبدیل به رشته JSON.
     * @return رشته JSON حاوی اطلاعات Percentage.
     */
    @TypeConverter
    fun fromPercentage(percentage: Percentage): String {
        return gson.toJson(percentage)
    }

    /**
     * تبدیل رشته JSON به Percentage.
     *
     * @param percentageJson رشته JSON حاوی اطلاعات Percentage.
     * @return شیء Percentage حاوی اطلاعات خوانده شده از رشته JSON.
     */
    @TypeConverter
    fun toPercentage(percentageJson: String): Percentage {
        return gson.fromJson(percentageJson, Percentage::class.java)
    }
}

