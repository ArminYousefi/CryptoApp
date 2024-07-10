package google.yousefi.cryptoapp.domain.favourites

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.favouriteCoinId.FavouriteCoinIdRepository
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * یک کلاس برای بررسی اینکه آیا یک سکه خاص در لیست سکه‌های مورد علاقه کاربر است.
 * @param favouriteCoinIdRepository ریپازیتوری که اطلاعات لازم برای کار خود را دارد.
 */
class IsCoinFavouriteUseCase @Inject constructor(
    private val favouriteCoinIdRepository: FavouriteCoinIdRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @param favouriteCoinId شناسه سکه‌ی مورد نظر برای بررسی.
     * @return جریانی از نوع Result که نتیجه بررسی (آیا سکه مورد نظر مورد علاقه است یا خیر) را حاوی می‌شود.
     */
    operator fun invoke(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return isCoinFavourite(favouriteCoinId = favouriteCoinId)
    }

    /**
     * یک متد که بررسی می‌کند آیا یک سکه خاص در لیست سکه‌های مورد علاقه کاربر است.
     *
     * @param favouriteCoinId شناسه سکه‌ی مورد نظر برای بررسی.
     * @return جریانی از نوع Result که نتیجه بررسی (آیا سکه مورد نظر مورد علاقه است یا خیر) را حاوی می‌شود.
     */
    private fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return favouriteCoinIdRepository.isCoinFavourite(favouriteCoinId = favouriteCoinId)
    }
}
