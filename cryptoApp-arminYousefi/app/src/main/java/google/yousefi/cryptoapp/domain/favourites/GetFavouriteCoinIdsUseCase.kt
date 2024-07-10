package google.yousefi.cryptoapp.domain.favourites

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.favouriteCoinId.FavouriteCoinIdRepository
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase برای دریافت لیست شناسه‌های سکه‌های مورد علاقه.
 *
 * @param favouriteCoinIdRepository ریپازیتوری برای دسترسی به داده‌های شناسه‌های سکه‌های مورد علاقه.
 */
class GetFavouriteCoinIdsUseCase @Inject constructor(
    private val favouriteCoinIdRepository: FavouriteCoinIdRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @return جریان از نوع Result که لیست شناسه‌های سکه‌های مورد علاقه را در بر دارد.
     */
    operator fun invoke(): Flow<Result<List<FavouriteCoinId>>> {
        return getFavouriteCoinIds()
    }

    /**
     * دریافت لیست شناسه‌های سکه‌های مورد علاقه.
     *
     * @return جریان از نوع Result که لیست شناسه‌های سکه‌های مورد علاقه را در بر دارد.
     */
    private fun getFavouriteCoinIds(): Flow<Result<List<FavouriteCoinId>>> {
        return favouriteCoinIdRepository.getFavouriteCoinIds()
    }
}
