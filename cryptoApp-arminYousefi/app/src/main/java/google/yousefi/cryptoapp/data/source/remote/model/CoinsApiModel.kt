package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 * مدل داده برای اطلاعات سکه‌ها از API است.
 *
 * @property coinsData اطلاعات سکه‌ها که شامل لیستی از مدل‌های سکه است.
 */
data class CoinsApiModel(
    @SerializedName("data")
    val coinsData: CoinsData?
)

/**
 * مدل داده برای داده‌های سکه‌ها است.
 *
 * @property coins لیستی از مدل‌های سکه.
 */
data class CoinsData(
    @SerializedName("coins")
    val coins: List<CoinApiModel?>?
)

/**
 * مدل داده برای اطلاعات یک سکه است.
 *
 * @property id شناسه یکتای سکه.
 * @property symbol نماد سکه.
 * @property name نام سکه.
 * @property imageUrl URL تصویر آیکون سکه.
 * @property currentPrice قیمت فعلی سکه.
 * @property priceChangePercentage24h درصد تغییر قیمت در ۲۴ ساعت گذشته.
 */
data class CoinApiModel(
    @SerializedName("uuid")
    val id: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("iconUrl")
    val imageUrl: String?,
    @SerializedName("price")
    val currentPrice: String?,
    @SerializedName("change")
    val priceChangePercentage24h: String?
)
