package google.yousefi.cryptoapp.data.repository.coin

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import kotlinx.coroutines.flow.Flow

/**
 * رابط CoinRepository برای ارتباط با داده‌های مربوط به سکه‌ها، شامل دریافت داده‌های از راه دور و داده‌های ذخیره شده استفاده می‌شود.
 */
interface CoinRepository {

    /**
     * متدی برای دریافت داده‌های سکه‌ها از راه دور با مرتب سازی و ارز مشخص.
     *
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس کدام ویژگی.
     * @param currency واحد پولی برای نمایش قیمت‌ها.
     * @return Result<List<Coin>> نتیجه دریافت داده‌های سکه‌ها از راه دور.
     */
    suspend fun getRemoteCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<Coin>>

    /**
     * جریانی برای دریافت داده‌های سکه‌های ذخیره شده.
     *
     * @return Flow<Result<List<Coin>>> جریانی که نتیجه دریافت داده‌های سکه‌های ذخیره شده را ارائه می‌دهد.
     */
    fun getCachedCoins(): Flow<Result<List<Coin>>>

    /**
     * متدی برای به روزرسانی داده‌های سکه‌های ذخیره شده.
     *
     * @param coins لیستی از داده‌های سکه‌ها برای به روزرسانی.
     */
    suspend fun updateCachedCoins(coins: List<Coin>)
}
