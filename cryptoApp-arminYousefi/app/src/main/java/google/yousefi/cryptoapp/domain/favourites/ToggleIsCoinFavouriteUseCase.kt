package google.yousefi.cryptoapp.domain.favourites

import google.yousefi.cryptoapp.data.repository.favouriteCoinId.FavouriteCoinIdRepository
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import javax.inject.Inject

/**
 * یک کلاس برای تغییر وضعیت علاقه‌مندی یک سکه در لیست سکه‌های مورد علاقه کاربر است.
 * @param favouriteCoinIdRepository ریپازیتوری که اطلاعات لازم برای کار خود را دارد.
 */
class ToggleIsCoinFavouriteUseCase @Inject constructor(
    private val favouriteCoinIdRepository: FavouriteCoinIdRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را اجرا می‌کند.
     *
     * @param favouriteCoinId شناسه سکه‌ی مورد نظر برای تغییر وضعیت علاقه‌مندی.
     */
    suspend operator fun invoke(favouriteCoinId: FavouriteCoinId) {
        return toggleIsCoinFavourite(favouriteCoinId = favouriteCoinId)
    }

    /**
     * یک متد که وضعیت علاقه‌مندی یک سکه در لیست سکه‌های مورد علاقه را تغییر می‌دهد.
     *
     * @param favouriteCoinId شناسه سکه‌ی مورد نظر برای تغییر وضعیت علاقه‌مندی.
     */
    private suspend fun toggleIsCoinFavourite(favouriteCoinId: FavouriteCoinId) {
        return favouriteCoinIdRepository.toggleIsCoinFavourite(favouriteCoinId = favouriteCoinId)
    }
}
