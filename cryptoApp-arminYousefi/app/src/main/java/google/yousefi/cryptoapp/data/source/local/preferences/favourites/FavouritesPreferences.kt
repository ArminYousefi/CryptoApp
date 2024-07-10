package google.yousefi.cryptoapp.data.source.local.preferences.favourites

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import kotlinx.serialization.Serializable

/**
 * یک کلاس داده‌ای که ترجیحات مربوط به لیست علاقه‌مندی‌ها را نگهداری می‌کند.
 *
 * @property isFavouritesCondensed نشان‌دهنده وضعیت فشرده‌سازی لیست علاقه‌مندی‌ها است.
 *                                پیش‌فرض false است.
 * @property coinSort نوع مرتب‌سازی ارزهای مورد علاقه بر اساس enum CoinSort.
 *                    پیش‌فرض MarketCap است.
 */
@Serializable
data class FavouritesPreferences(
    val isFavouritesCondensed: Boolean = false,
    val coinSort: CoinSort = CoinSort.MarketCap
)

