package google.yousefi.cryptoapp.data.source.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import google.yousefi.cryptoapp.model.Price

/**
 * کلاس PriceTypeConverter برای تبدیل نوع داده Price به و از رشته JSON استفاده می‌شود.
 * این کلاس برای استفاده در Room Database به عنوان یک TypeConverter طراحی شده است.
 *
 * @property gson یک شیء از کتابخانه Gson برای انجام تبدیل‌های JSON به اشیاء و بالعکس.
 * @constructor سازنده کلاس برای مقداردهی اولیه شیء Gson.
 */
class PriceTypeConverter {
    private val gson = Gson()

    /**
     * تبدیل Price به رشته JSON.
     *
     * @param price شیء Price برای تبدیل به رشته JSON.
     * @return رشته JSON حاوی اطلاعات Price.
     */
    @TypeConverter
    fun fromPrice(price: Price): String {
        return gson.toJson(price)
    }

    /**
     * تبدیل رشته JSON به Price.
     *
     * @param priceJson رشته JSON حاوی اطلاعات Price.
     * @return شیء Price حاوی اطلاعات خوانده شده از رشته JSON.
     */
    @TypeConverter
    fun toPrice(priceJson: String): Price {
        return gson.fromJson(priceJson, Price::class.java)
    }
}

