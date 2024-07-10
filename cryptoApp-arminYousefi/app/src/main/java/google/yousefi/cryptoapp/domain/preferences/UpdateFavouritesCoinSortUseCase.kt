package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.favourites.FavouritesPreferencesRepository
import javax.inject.Inject

/**
 * UseCase برای به روزرسانی نوع مرتب‌سازی سکه‌های مورد علاقه کاربر.
 *
 * @param favouritesPreferencesRepository ریپازیتوری مربوط به ترجیحات کاربر در مورد سکه‌های مورد علاقه.
 */
class UpdateFavouritesCoinSortUseCase @Inject constructor(
    private val favouritesPreferencesRepository: FavouritesPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به روزرسانی نوع مرتب‌سازی سکه‌های مورد علاقه اجرا می‌کند.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای سکه‌های مورد علاقه.
     */
    suspend operator fun invoke(coinSort: CoinSort) {
        updateFavouritesCoinSort(coinSort = coinSort)
    }

    /**
     * یک متد خصوصی برای به روزرسانی نوع مرتب‌سازی در ریپازیتوری مربوطه.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای سکه‌های مورد علاقه.
     */
    private suspend fun updateFavouritesCoinSort(coinSort: CoinSort) {
        favouritesPreferencesRepository.updateCoinSort(coinSort = coinSort)
    }
}

