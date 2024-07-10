package google.yousefi.cryptoapp.data.repository.favouriteCoin

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow

/**
 * رابط FavouriteCoinRepository برای ارتباط با منبع داده و مدیریت سکه‌های مورد علاقه کاربر استفاده می‌شود.
 */
interface FavouriteCoinRepository {

    /**
     * متدی برای دریافت سکه‌های مورد علاقه کاربر از راه دور با استفاده از شناسه‌های سکه‌ها، مرتب‌سازی و واحد پولی.
     *
     * @param coinIds لیست شناسه‌های سکه‌های مورد علاقه کاربر برای دریافت.
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس کدام ویژگی.
     * @param currency واحد پولی برای نمایش قیمت‌ها.
     * @return Result<List<FavouriteCoin>> نتیجه دریافت سکه‌های مورد علاقه کاربر از راه دور.
     */
    suspend fun getRemoteFavouriteCoins(
        coinIds: List<String>,
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<FavouriteCoin>>

    /**
     * جریانی برای دریافت سکه‌های مورد علاقه کاربر ذخیره شده.
     *
     * @return Flow<Result<List<FavouriteCoin>>> جریانی که نتیجه دریافت سکه‌های مورد علاقه کاربر ذخیره شده را ارائه می‌دهد.
     */
    fun getCachedFavouriteCoins(): Flow<Result<List<FavouriteCoin>>>

    /**
     * متدی برای به روزرسانی سکه‌های مورد علاقه کاربر ذخیره شده.
     *
     * @param favouriteCoins لیستی از سکه‌های مورد علاقه کاربر برای به روزرسانی.
     */
    suspend fun updateCachedFavouriteCoins(favouriteCoins: List<FavouriteCoin>)
}
