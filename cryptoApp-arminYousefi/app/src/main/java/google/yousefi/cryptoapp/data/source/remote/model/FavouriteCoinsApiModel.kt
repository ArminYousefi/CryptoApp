package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * مدل داده برای اطلاعات سکه‌های مورد علاقه از API است.
 *
 * @property favouriteCoinsData اطلاعات سکه‌های مورد علاقه که شامل لیستی از مدل‌های سکه‌های مورد علاقه است.
 */
data class FavouriteCoinsApiModel(
    @SerializedName("data")
    val favouriteCoinsData: FavouriteCoinsData?
)

/**
 * مدل داده برای داده‌های سکه‌های مورد علاقه است.
 *
 * @property favouriteCoins لیستی از مدل‌های سکه‌های مورد علاقه.
 */
data class FavouriteCoinsData(
    @SerializedName("coins")
    val favouriteCoins: List<FavouriteCoinApiModel?>?
)

/**
 * مدل داده برای اطلاعات یک سکه مورد علاقه است.
 *
 * @property id شناسه یکتای سکه.
 * @property symbol نماد سکه.
 * @property name نام سکه.
 * @property imageUrl URL تصویر آیکون سکه.
 * @property currentPrice قیمت فعلی سکه.
 * @property priceChangePercentage24h درصد تغییر قیمت در ۲۴ ساعت گذشته.
 * @property prices24h نمودار قیمت‌های ۲۴ ساعت گذشته برای سکه.
 */
data class FavouriteCoinApiModel(
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
    val priceChangePercentage24h: String?,
    @SerializedName("sparkline")
    val prices24h: List<BigDecimal?>?
)
