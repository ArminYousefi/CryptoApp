package google.yousefi.cryptoapp.domain.market

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.repository.marketStats.MarketStatsRepository
import google.yousefi.cryptoapp.model.MarketStats
import javax.inject.Inject

/**
 * یک UseCase برای دریافت آمار بازار است.
 *
 * @param marketStatsRepository ریپازیتوری مربوط به آمار بازار.
 */
class GetMarketStatsUseCase @Inject constructor(
    private val marketStatsRepository: MarketStatsRepository
) {
    /**
     * اپراتور فراخوانی که این UseCase را بدون پارامتر اجرا می‌کند.
     *
     * @return یک Result از نوع MarketStats که حاوی آمار بازار است یا خطا در صورت وقوع خطا.
     */
    suspend operator fun invoke(): Result<MarketStats> {
        return getMarketStats()
    }

    /**
     * یک متد خصوصی برای دریافت آمار بازار از ریپازیتوری.
     *
     * @return یک Result از نوع MarketStats که حاوی آمار بازار است یا خطا در صورت وقوع خطا.
     */
    private suspend fun getMarketStats(): Result<MarketStats> {
        return marketStatsRepository.getMarketStats()
    }
}
