package google.yousefi.cryptoapp.data.repository.marketStats

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.model.MarketStats

/**
 * رابط MarketStatsRepository برای ارتباط با منبع داده‌های بازار و دریافت آمارهای بازار مورد نیاز استفاده می‌شود.
 */
interface MarketStatsRepository {

    /**
     * متدی برای دریافت آمارهای بازار.
     *
     * @return Result<MarketStats> نتیجه دریافت آمارهای بازار یا خطا در صورت وجود مشکل.
     */
    suspend fun getMarketStats(): Result<MarketStats>
}

