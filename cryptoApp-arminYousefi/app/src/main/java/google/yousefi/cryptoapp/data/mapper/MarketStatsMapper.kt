package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.data.source.remote.model.MarketStatsApiModel
import google.yousefi.cryptoapp.model.MarketStats
import google.yousefi.cryptoapp.model.Percentage
import javax.inject.Inject

/**
 * کلاس MarketStatsMapper برای تبدیل مدل API به مدل داخلی آمار بازار استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class MarketStatsMapper @Inject constructor() {

    /**
     * متدی برای تبدیل مدل API به مدل MarketStats.
     *
     * @param apiModel مدل API که شامل داده‌های آمار بازار است.
     * @return MarketStats مدل MarketStats که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(apiModel: MarketStatsApiModel): MarketStats {
        val marketStats = apiModel.marketStatsDataHolder?.marketStatsData

        return MarketStats(
            marketCapChangePercentage24h = Percentage(marketStats?.marketCapChangePercentage24h)
        )
    }
}
