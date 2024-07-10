package google.yousefi.cryptoapp.data.source.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.math.BigDecimal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

/**
 * کلاس ImmutableListTypeConverter برای تبدیل نوع داده ImmutableList<BigDecimal> به و از رشته JSON استفاده می‌شود.
 * این کلاس برای استفاده در Room Database به عنوان یک TypeConverter طراحی شده است.
 *
 * @property gson یک شیء از کتابخانه Gson برای انجام تبدیل‌های JSON به اشیاء و بالعکس.
 * @constructor سازنده کلاس برای مقداردهی اولیه شیء Gson.
 */
class ImmutableListTypeConverter {
    private val gson = Gson()

    /**
     * تبدیل ImmutableList<BigDecimal> به رشته JSON.
     *
     * @param immutableBigDecimals لیست غیرقابل تغییر از اعداد دهی‌های معین برای تبدیل به رشته JSON.
     * @return رشته JSON حاوی اطلاعات ImmutableList<BigDecimal>.
     */
    @TypeConverter
    fun fromImmutableList(immutableBigDecimals: ImmutableList<BigDecimal>): String {
        return gson.toJson(immutableBigDecimals)
    }

    /**
     * تبدیل رشته JSON به ImmutableList<BigDecimal>.
     *
     * @param immutableBigDecimalsJson رشته JSON حاوی اطلاعات ImmutableList<BigDecimal>.
     * @return ImmutableList<BigDecimal> حاوی اطلاعات خوانده شده از رشته JSON.
     */
    @TypeConverter
    fun toImmutableList(immutableBigDecimalsJson: String): ImmutableList<BigDecimal> {
        val type: Type = object : TypeToken<ImmutableList<BigDecimal>>() {}.type
        return gson.fromJson<ImmutableList<BigDecimal>>(immutableBigDecimalsJson, type)
            .toPersistentList()
    }
}

