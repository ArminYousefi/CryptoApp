package google.yousefi.cryptoapp.data.repository.favouriteCoin

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.mapper.FavouriteCoinMapper
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSource
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
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
 * کلاس FavouriteCoinRepositoryImpl برای اجرای رابط FavouriteCoinRepository و ارتباط با منابع داده محلی و شبکه سکه‌های مورد علاقه کاربر استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت سکه‌های مورد علاقه از API.
 * @property coinLocalDataSource منبع داده محلی برای دریافت و ذخیره سکه‌های مورد علاقه کاربر.
 * @property favouriteCoinMapper Mapper برای تبدیل مدل API به مدل داخلی FavouriteCoin.
 * @property ioDispatcher Dispatcher برای اجرای کدهای مربوط به شبکه در یک thread جداگانه.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class FavouriteCoinRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinLocalDataSource: CoinLocalDataSource,
    private val favouriteCoinMapper: FavouriteCoinMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavouriteCoinRepository {

    /**
     * متدی برای دریافت سکه‌های مورد علاقه کاربر از راه دور با استفاده از شناسه‌های سکه‌ها، مرتب‌سازی و واحد پولی.
     *
     * @param coinIds لیست شناسه‌های سکه‌های مورد علاقه کاربر برای دریافت.
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس کدام ویژگی.
     * @param currency واحد پولی برای نمایش قیمت‌ها.
     * @return Result<List<FavouriteCoin>> نتیجه دریافت سکه‌های مورد علاقه کاربر از راه دور.
     */
    override suspend fun getRemoteFavouriteCoins(
        coinIds: List<String>,
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<FavouriteCoin>> {
        if (coinIds.isEmpty()) {
            return Result.Success(emptyList())
        }

        return try {
            val response = coinNetworkDataSource.getFavouriteCoins(
                coinIds = coinIds,
                coinSort = coinSort,
                currency = currency
            )

            val body = response.body()

            if (response.isSuccessful && body?.favouriteCoinsData?.favouriteCoins != null) {
                val favouriteCoins = favouriteCoinMapper.mapApiModelToModel(
                    apiModel = body,
                    currency = currency
                )
                Result.Success(favouriteCoins)
            } else {
                Timber.e(
                    "getRemoteFavouriteCoins unsuccessful retrofit response ${response.message()}"
                )
                Result.Error("Unable to fetch network favourite coins list")
            }
        } catch (e: Exception) {
            Timber.e("getRemoteFavouriteCoins error ${e.message}")
            Result.Error("Unable to fetch network favourite coins list")
        }
    }

    /**
     * جریانی برای دریافت سکه‌های مورد علاقه کاربر ذخیره شده.
     *
     * @return Flow<Result<List<FavouriteCoin>>> جریانی که نتیجه دریافت سکه‌های مورد علاقه کاربر ذخیره شده را ارائه می‌دهد.
     */
    override fun getCachedFavouriteCoins(): Flow<Result<List<FavouriteCoin>>> {
        return coinLocalDataSource.getFavouriteCoins()
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("getCachedFavouriteCoins error ${e.message}")
                Result.Error<List<Coin>>("Unable to fetch cached favourite coins")
            }.flowOn(ioDispatcher)
    }

    /**
     * متدی برای به روزرسانی سکه‌های مورد علاقه کاربر ذخیره شده.
     *
     * @param favouriteCoins لیستی از سکه‌های مورد علاقه کاربر برای به روزرسانی.
     */
    override suspend fun updateCachedFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        withContext(ioDispatcher) {
            coinLocalDataSource.updateFavouriteCoins(favouriteCoins)
        }
    }
}

