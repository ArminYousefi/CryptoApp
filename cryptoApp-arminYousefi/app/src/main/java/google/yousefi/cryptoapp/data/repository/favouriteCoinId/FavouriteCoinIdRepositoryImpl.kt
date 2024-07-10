package google.yousefi.cryptoapp.data.repository.favouriteCoinId

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.database.CoinLocalDataSource
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import google.yousefi.cryptoapp.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * کلاس FavouriteCoinIdRepositoryImpl برای اجرای رابط FavouriteCoinIdRepository و مدیریت شناسه‌های سکه‌های مورد علاقه کاربر در داده‌های محلی استفاده می‌شود.
 *
 * @property coinLocalDataSource منبع داده محلی برای دریافت و ذخیره شناسه‌های سکه‌های مورد علاقه کاربر.
 * @property ioDispatcher Dispatcher برای اجرای کدهای مربوط به شبکه در یک thread جداگانه.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class FavouriteCoinIdRepositoryImpl @Inject constructor(
    private val coinLocalDataSource: CoinLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavouriteCoinIdRepository {

    /**
     * جریانی برای دریافت تمام شناسه‌های سکه‌های مورد علاقه کاربر از داده‌های محلی.
     *
     * @return Flow<Result<List<FavouriteCoinId>>> جریانی که نتیجه دریافت تمام شناسه‌های سکه‌های مورد علاقه کاربر را ارائه می‌دهد.
     */
    override fun getFavouriteCoinIds(): Flow<Result<List<FavouriteCoinId>>> {
        return coinLocalDataSource.getFavouriteCoinIds()
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("getFavouriteCoinIds error ${e.message}")
                Result.Error<List<FavouriteCoinId>>("Unable to fetch favourite coin ids")
            }
            .flowOn(ioDispatcher)
    }

    /**
     * جریانی برای بررسی اینکه آیا یک شناسه سکه مورد علاقه کاربر است یا خیر.
     *
     * @param favouriteCoinId شناسه سکه‌ای که برای بررسی مورد نیاز است.
     * @return Flow<Result<Boolean>> جریانی که نتیجه بررسی بودن شناسه سکه مورد علاقه کاربر را ارائه می‌دهد.
     */
    override fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return coinLocalDataSource.isCoinFavourite(favouriteCoinId = favouriteCoinId)
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("isCoinFavourite error ${e.message}")
                Result.Error<Boolean>("Unable to fetch if coin is favourite")
            }
            .flowOn(ioDispatcher)
    }

    /**
     * متدی برای تغییر وضعیت مورد علاقه بودن یک شناسه سکه در داده‌های محلی.
     *
     * @param favouriteCoinId شناسه سکه‌ای که برای تغییر وضعیت مورد علاقه بودن آن مورد نیاز است.
     */
    override suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId) {
        withContext(ioDispatcher) {
            coinLocalDataSource.toggleIsCoinFavourite(favouriteCoinId)
        }
    }
}

