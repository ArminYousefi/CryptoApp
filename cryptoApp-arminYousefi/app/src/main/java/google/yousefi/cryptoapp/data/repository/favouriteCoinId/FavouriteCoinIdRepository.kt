package google.yousefi.cryptoapp.data.repository.favouriteCoinId

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

/**
 * رابط FavouriteCoinIdRepository برای ارتباط با منابع داده و مدیریت شناسه‌های سکه‌های مورد علاقه کاربر استفاده می‌شود.
 */
interface FavouriteCoinIdRepository {

    /**
     * جریانی برای دریافت تمام شناسه‌های سکه‌های مورد علاقه کاربر.
     *
     * @return Flow<Result<List<FavouriteCoinId>>> جریانی که نتیجه دریافت تمام شناسه‌های سکه‌های مورد علاقه کاربر را ارائه می‌دهد.
     */
    fun getFavouriteCoinIds(): Flow<Result<List<FavouriteCoinId>>>

    /**
     * جریانی برای بررسی اینکه آیا یک شناسه سکه مورد علاقه کاربر است یا خیر.
     *
     * @param favouriteCoinId شناسه سکه‌ای که برای بررسی مورد نیاز است.
     * @return Flow<Result<Boolean>> جریانی که نتیجه بررسی بودن شناسه سکه مورد علاقه کاربر را ارائه می‌دهد.
     */
    fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>>

    /**
     * متدی برای تغییر وضعیت مورد علاقه بودن یک شناسه سکه.
     *
     * @param favouriteCoinId شناسه سکه‌ای که برای تغییر وضعیت مورد علاقه بودن آن مورد نیاز است.
     */
    suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId)
}

