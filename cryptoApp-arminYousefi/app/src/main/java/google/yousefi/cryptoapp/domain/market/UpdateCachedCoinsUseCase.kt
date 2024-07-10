package google.yousefi.cryptoapp.domain.market

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.coin.CoinRepository
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import javax.inject.Inject

/**
 * UseCase برای به‌روزرسانی کوین‌های ذخیره‌شده است.
 *
 * @param coinRepository ریپازیتوری مربوط به کوین‌ها.
 */
class UpdateCachedCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به‌روزرسانی کوین‌های ذخیره‌شده با ترتیب و ارز مشخص شده اجرا می‌کند.
     *
     * @param coinSort ترتیب مورد نظر برای کوین‌ها بر اساس CoinSort.
     * @param currency ارز مرجع برای قیمت‌ها و معاملات.
     * @return یک Result از نوع List<Coin> که حاوی لیست کوین‌های به‌روزرسانی شده است یا خطا در صورت وقوع خطا.
     */
    suspend operator fun invoke(
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<Coin>> {
        return refreshCachedCoins(coinSort = coinSort, currency = currency)
    }

    /**
     * یک متد خصوصی برای به‌روزرسانی کوین‌های ذخیره‌شده با ترتیب و ارز مشخص شده است.
     *
     * @param coinSort ترتیب مورد نظر برای کوین‌ها بر اساس CoinSort.
     * @param currency ارز مرجع برای قیمت‌ها و معاملات.
     * @return یک Result از نوع List<Coin> که حاوی لیست کوین‌های به‌روزرسانی شده است یا خطا در صورت وقوع خطا.
     */
    private suspend fun refreshCachedCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Result<List<Coin>> {
        val remoteCoinsResult = coinRepository.getRemoteCoins(
            coinSort = coinSort,
            currency = currency
        )

        if (remoteCoinsResult is Result.Success) {
            val coins = remoteCoinsResult.data
            coinRepository.updateCachedCoins(coins = coins)
        }

        return remoteCoinsResult
    }
}
