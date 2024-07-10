package google.yousefi.cryptoapp.data.repository.marketStats

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.mapper.MarketStatsMapper
import google.yousefi.cryptoapp.data.source.remote.CoinNetworkDataSource
import google.yousefi.cryptoapp.model.MarketStats
import javax.inject.Inject
import timber.log.Timber

/**
 * کلاس MarketStatsRepositoryImpl برای اجرای رابط MarketStatsRepository و دریافت آمارهای بازار از منبع داده‌های شبکه استفاده می‌شود.
 *
 * @property coinNetworkDataSource منبع داده شبکه برای دریافت آمارهای بازار.
 * @property marketStatsMapper Mapper برای تبدیل مدل‌های داده‌ای شبکه به مدل‌های داده‌ای داخلی.
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class MarketStatsRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val marketStatsMapper: MarketStatsMapper
) : MarketStatsRepository {

    /**
     * متدی برای دریافت آمارهای بازار از منبع داده شبکه.
     *
     * @return Result<MarketStats> نتیجه دریافت آمارهای بازار یا خطا در صورت وجود مشکل.
     */
    override suspend fun getMarketStats(): Result<MarketStats> {
        return try {
            val response = coinNetworkDataSource.getMarketStats()
            val body = response.body()

            if (response.isSuccessful && body?.marketStatsDataHolder?.marketStatsData != null) {
                val marketStats = marketStatsMapper.mapApiModelToModel(body)
                Result.Success(marketStats)
            } else {
                Timber.e(
                    "getMarketStats unsuccessful retrofit response ${response.message()}"
                )
                Result.Error("Unable to fetch market stats")
            }
        } catch (e: Exception) {
            Timber.e("getMarketStats error ${e.message}")
            Result.Error("Unable to fetch market stats")
        }
    }
}
