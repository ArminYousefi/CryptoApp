package google.yousefi.cryptoapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 * مدل داده برای آمار بازار از API است.
 *
 * @property marketStatsDataHolder نگهدارنده اطلاعات آمار بازار که شامل اطلاعات دقیق آمار بازار است.
 */
data class MarketStatsApiModel(
    @SerializedName("data")
    val marketStatsDataHolder: MarketStatsDataHolder?
)

/**
 * کلاس نگهدارنده اطلاعات آمار بازار است.
 *
 * @property marketStatsData اطلاعات آمار بازار که شامل تغییرات سرمایه‌بازار در ۲۴ ساعت گذشته است.
 */
data class MarketStatsDataHolder(
    @SerializedName("stats")
    val marketStatsData: MarketStatsData?
)

/**
 * مدل داده برای اطلاعات آمار بازار است.
 *
 * @property marketCapChangePercentage24h درصد تغییر سرمایه‌بازار در ۲۴ ساعت گذشته.
 */
data class MarketStatsData(
    @SerializedName("marketCapChange")
    val marketCapChangePercentage24h: String?
)
