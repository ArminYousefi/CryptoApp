package google.yousefi.cryptoapp.data.source.local.preferences.market

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import kotlinx.serialization.Serializable

/**
 * یک کلاس داده‌ای که تنظیمات بازار را نگهداری می‌کند، شامل نوع مرتب‌سازی ارزها.
 *
 * @property coinSort نوع مرتب‌سازی ارزها که توسط enum CoinSort تعیین می‌شود.
 */
@Serializable
data class MarketPreferences(
    val coinSort: CoinSort = CoinSort.MarketCap
)
