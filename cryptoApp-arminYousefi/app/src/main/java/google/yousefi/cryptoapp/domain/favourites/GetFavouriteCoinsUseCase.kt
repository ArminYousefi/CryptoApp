package google.yousefi.cryptoapp.domain.favourites

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.favouriteCoin.FavouriteCoinRepository
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * یک کلاس که برای دریافت لیست سکه‌های مورد علاقه استفاده می‌شود.
 * @param favouriteCoinRepository ریپازیتوری که اطلاعات لازم برای کار خود را دارد.
 */
class GetFavouriteCoinsUseCase @Inject constructor(
    private val favouriteCoinRepository: FavouriteCoinRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @return جریانی از نوع Result که لیست سکه‌های مورد علاقه را حاوی می‌شود.
     */
    operator fun invoke(): Flow<Result<List<FavouriteCoin>>> {
        return getFavouriteCoins()
    }

    /**
     * یک متد که لیست سکه‌های مورد علاقه را حاوی می‌شود.
     *
     * @return جریانی از نوع Result که لیست سکه‌های مورد علاقه را حاوی می‌شود.
     */
    private fun getFavouriteCoins(): Flow<Result<List<FavouriteCoin>>> {
        return favouriteCoinRepository.getCachedFavouriteCoins()
    }
}
