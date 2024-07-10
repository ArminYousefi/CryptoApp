package google.yousefi.cryptoapp.domain.favourites

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.favouriteCoin.FavouriteCoinRepository
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import javax.inject.Inject

/**
 * یک کلاس برای به‌روزرسانی لیست سکه‌های مورد علاقه در حافظه نهان است.
 *
 * @param favouriteCoinRepository ریپازیتوری مربوط به لیست سکه‌های مورد علاقه.
 */
class UpdateCachedFavouriteCoinsUseCase @Inject constructor(
    private val favouriteCoinRepository: FavouriteCoinRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را با پارامترهای مورد نیاز اجرا می‌کند.
     *
     * @param coinIds شناسه‌های سکه‌های مورد علاقه برای به‌روزرسانی.
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس ویژگی خاص.
     * @param currency ارز مرجع برای دریافت قیمت سکه‌ها.
     * @return نتیجه‌ای از نوع Result<List<FavouriteCoin>> که شامل لیست سکه‌های مورد علاقه بروز شده است یا خطا در صورت وقوع خطا.
     */
    suspend operator fun invoke(
        coinIds: List<FavouriteCoinId>,
        coinSort: CoinSort,
        currency: Currency,
    ): Result<List<FavouriteCoin>> {
        return updateCachedFavouriteCoins(
            coinIds = coinIds,
            coinSort = coinSort,
            currency = currency,
        )
    }

    /**
     * یک متد که لیست سکه‌های مورد علاقه را در حافظه نهان به‌روزرسانی می‌کند.
     *
     * @param coinIds شناسه‌های سکه‌های مورد علاقه برای به‌روزرسانی.
     * @param coinSort نوع مرتب‌سازی سکه‌ها بر اساس ویژگی خاص.
     * @param currency ارز مرجع برای دریافت قیمت سکه‌ها.
     * @return نتیجه‌ای از نوع Result<List<FavouriteCoin>> که شامل لیست سکه‌های مورد علاقه بروز شده است یا خطا در صورت وقوع خطا.
     */
    private suspend fun updateCachedFavouriteCoins(
        coinIds: List<FavouriteCoinId>,
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<FavouriteCoin>> {
        // دریافت لیست سکه‌های مورد علاقه از سرور
        val remoteFavouriteCoinsResult = favouriteCoinRepository.getRemoteFavouriteCoins(
            coinIds = coinIds.map { it.id },
            coinSort = coinSort,
            currency = currency,
        )

        // اگر دریافت اطلاعات با موفقیت انجام شود
        if (remoteFavouriteCoinsResult is Result.Success) {
            val favouriteCoins = remoteFavouriteCoinsResult.data
            // به‌روزرسانی لیست سکه‌های مورد علاقه در حافظه نهان
            favouriteCoinRepository.updateCachedFavouriteCoins(favouriteCoins = favouriteCoins)
        }

        return remoteFavouriteCoinsResult
    }
}
