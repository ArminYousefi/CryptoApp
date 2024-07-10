package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 * مدل داده برای جزئیات سکه از API است.
 *
 * @property coinDetailsDataHolder نگهدارنده اطلاعات جزئیات سکه که شامل اطلاعات دقیق سکه است.
 */
data class CoinDetailsApiModel(
    @SerializedName("data")
    val coinDetailsDataHolder: CoinDetailsDataHolder?
)

/**
 * کلاس نگهدارنده اطلاعات جزئیات سکه است.
 *
 * @property coinDetailsData اطلاعات جزئیات سکه که شامل مشخصات متنوعی از سکه است.
 */
data class CoinDetailsDataHolder(
    @SerializedName("coin")
    val coinDetailsData: CoinDetailsData?
)

/**
 * مدل داده برای اطلاعات جزئیات یک سکه است.
 *
 * @property id شناسه یکتای سکه.
 * @property name نام سکه.
 * @property symbol نماد سکه.
 * @property imageUrl URL تصویر آیکون سکه.
 * @property currentPrice قیمت فعلی سکه.
 * @property marketCap سرمایه‌بازار سکه.
 * @property marketCapRank رتبه بر اساس سرمایه‌بازار.
 * @property volume24h حجم معاملات 24 ساعته.
 * @property supply اطلاعات مربوط به تامین سکه، شامل تأمین موجود و اطلاعات مرتبط.
 * @property allTimeHigh اطلاعات مربوط به بیشترین قیمت تمام زمانه سکه، شامل قیمت و زمان مرتبط.
 * @property listedAt زمان لیست شدن سکه.
 */
data class CoinDetailsData(
    @SerializedName("uuid")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("iconUrl")
    val imageUrl: String?,
    @SerializedName("price")
    val currentPrice: String?,
    @SerializedName("marketCap")
    val marketCap: String?,
    @SerializedName("rank")
    val marketCapRank: String?,
    @SerializedName("24hVolume")
    val volume24h: String?,
    @SerializedName("supply")
    val supply: Supply?,
    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh?,
    @SerializedName("listedAt")
    val listedAt: Long?
)

/**
 * مدل داده برای اطلاعات تأمین سکه.
 *
 * @property circulatingSupply تأمین موجود سکه.
 */
data class Supply(
    @SerializedName("circulating")
    val circulatingSupply: String?
)

/**
 * مدل داده برای اطلاعات بیشترین قیمت تمام زمانه سکه.
 *
 * @property price بیشترین قیمت تمام زمانه سکه.
 * @property timestamp زمان دستیابی به بیشترین قیمت تمام زمانه.
 */
data class AllTimeHigh(
    @SerializedName("price")
    val price: String?,
    @SerializedName("timestamp")
    val timestamp: Long?
)
