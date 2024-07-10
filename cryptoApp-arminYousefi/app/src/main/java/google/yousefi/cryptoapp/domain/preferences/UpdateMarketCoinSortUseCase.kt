package google.yousefi.cryptoapp.domain.preferences

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.market.MarketPreferencesRepository
import javax.inject.Inject

/**
 * UseCase برای به روزرسانی مرتب‌سازی ارزهای بازار.
 *
 * @param marketPreferencesRepository ریپازیتوری مربوط به تنظیمات بازار.
 */
class UpdateMarketCoinSortUseCase @Inject constructor(
    private val marketPreferencesRepository: MarketPreferencesRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را برای به روزرسانی مرتب‌سازی ارزهای بازار اجرا می‌کند.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای ارزهای بازار.
     */
    suspend operator fun invoke(coinSort: CoinSort) {
        updateMarketCoinSort(coinSort = coinSort)
    }

    /**
     * یک متد خصوصی برای به روزرسانی مرتب‌سازی ارزهای بازار در ریپازیتوری مربوطه.
     *
     * @param coinSort نوع مرتب‌سازی جدید برای ارزهای بازار.
     */
    private suspend fun updateMarketCoinSort(coinSort: CoinSort) {
        marketPreferencesRepository.updateCoinSort(coinSort = coinSort)
    }
}

