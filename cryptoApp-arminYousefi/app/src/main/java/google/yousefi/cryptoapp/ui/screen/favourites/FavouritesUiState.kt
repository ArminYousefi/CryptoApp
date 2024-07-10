package google.yousefi.cryptoapp.ui.screen.favourites

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * وضعیت UI برای صفحه‌ی محبوب‌ها شامل اطلاعات مربوط به سکه‌های محبوب و وضعیت‌های مختلف آن‌ها.
 *
 * @property favouriteCoins لیستی از سکه‌های محبوب
 * @property isFavouritesCondensed وضعیت فشرده یا غیرفشرده بودن لیست محبوب‌ها
 * @property coinSort نوع مرتب‌سازی سکه‌ها
 * @property isFavouriteCoinsEmpty آیا لیست سکه‌های محبوب خالی است یا نه
 * @property isRefreshing وضعیت بارگذاری مجدد داده‌ها
 * @property isLoading وضعیت بارگذاری داده‌ها
 * @property errorMessageIds لیستی از شناسه‌های پیغام خطاها
 */
data class FavouritesUiState(
    val favouriteCoins: ImmutableList<FavouriteCoin> = persistentListOf(),
    val isFavouritesCondensed: Boolean = false,
    val coinSort: CoinSort = CoinSort.MarketCap,
    val isFavouriteCoinsEmpty: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageIds: List<Int> = persistentListOf()
)
