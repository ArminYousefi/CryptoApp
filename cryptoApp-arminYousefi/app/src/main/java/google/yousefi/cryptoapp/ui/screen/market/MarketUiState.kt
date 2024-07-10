package google.yousefi.cryptoapp.ui.screen.market

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.ui.model.TimeOfDay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * وضعیت UI برای صفحه‌ی بازار شامل اطلاعات مربوط به لیست سکه‌ها و وضعیت‌های مختلف آن‌ها.
 *
 * @property coins لیستی از سکه‌ها
 * @property timeOfDay زمان روز (صبح، عصر، شب و ...)
 * @property marketCapChangePercentage24h درصد تغییرات بازار در ۲۴ ساعت اخیر
 * @property coinSort نوع مرتب‌سازی سکه‌ها
 * @property isRefreshing وضعیت بارگذاری مجدد داده‌ها
 * @property isLoading وضعیت بارگذاری داده‌ها
 * @property errorMessageIds لیستی از شناسه‌های پیغام خطاها
 */
data class MarketUiState(
    val coins: ImmutableList<Coin> = persistentListOf(),
    val timeOfDay: TimeOfDay = TimeOfDay.Morning,
    val marketCapChangePercentage24h: Percentage? = null,
    val coinSort: CoinSort = CoinSort.MarketCap,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageIds: List<Int> = persistentListOf()
)
