package google.yousefi.cryptoapp.data.repository.coin

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.mapper.CoinMapper
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSource
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * کلاس CoinRepositoryImpl برای اجرای رابط CoinRepository و مدیریت داده‌های مربوط به سکه‌ها در منابع داده محلی و شبکه استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت اطلاعات سکه‌ها از API.
 * @property coinLocalDataSource منبع داده محلی برای انجام عملیات مربوط به داده‌های سکه‌ها.
 * @property coinMapper Mapper برای تبدیل مدل API به مدل داخلی Coin.
 * @property ioDispatcher Dispatcher برای اجرای کدهای مربوط به شبکه در یک thread جداگانه.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
    private val coinMapper: CoinMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoinRepository {

    /**
     * متدی برای دریافت داده‌های سکه‌ها از راه دور با مرتب‌سازی و ارز مشخص.
     *
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس کدام ویژگی.
     * @param currency واحد پولی برای نمایش قیمت‌ها.
     * @return Result<List<Coin>> نتیجه دریافت داده‌های سکه‌ها از راه دور.
     */
    override suspend fun getRemoteCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<Coin>> = withContext(ioDispatcher) {
        try {
            val response = coinNetworkDataSource.getCoins(
                coinSort = coinSort,
                currency = currency
            )

            val body = response.body()

            if (response.isSuccessful && body?.coinsData != null) {
                val coins = coinMapper.mapApiModelToModel(body, currency = currency)
                Result.Success(coins)
            } else {
                Timber.e("getRemoteCoins unsuccessful retrofit response ${response.message()}")
                Result.Error("Unable to fetch network coins list")
            }
        } catch (e: Exception) {
            Timber.e("getRemoteCoins error ${e.message}")
            Result.Error("Unable to fetch network coins list")
        }
    }

    /**
     * جریانی برای دریافت داده‌های سکه‌های ذخیره شده.
     *
     * @return Flow<Result<List<Coin>>> جریانی که نتیجه دریافت داده‌های سکه‌های ذخیره شده را ارائه می‌دهد.
     */
    override fun getCachedCoins(): Flow<Result<List<Coin>>> {
        return coinLocalDataSource.getCoins()
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("getCachedCoins error ${e.message}")
                Result.Error<List<Coin>>("Unable to fetch cached coins")
            }
            .flowOn(ioDispatcher)
    }

    /**
     * متدی برای به روزرسانی داده‌های سکه‌های ذخیره شده.
     *
     * @param coins لیستی از داده‌های سکه‌ها برای به روزرسانی.
     */
    override suspend fun updateCachedCoins(coins: List<Coin>) {
        withContext(ioDispatcher) {
            coinLocalDataSource.updateCoins(coins)
        }
    }
}
