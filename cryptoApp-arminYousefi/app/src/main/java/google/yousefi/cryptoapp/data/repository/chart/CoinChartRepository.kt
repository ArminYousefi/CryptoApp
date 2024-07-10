package google.yousefi.cryptoapp.data.repository.chart

import google.yousefi.cryptoapp.common.Result
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.model.CoinChart
import kotlinx.coroutines.flow.Flow

/**
 * رابط CoinChartRepository برای ارتباط با منبع داده و دریافت نمودارهای سکه مورد نظر استفاده می‌شود.
 */
interface CoinChartRepository {

    /**
     * متدی برای دریافت نمودار سکه با استفاده از شناسه سکه، دوره نمودار و واحد پولی.
     *
     * @param coinId شناسه سکه برای دریافت نمودار.
     * @param chartPeriod دوره زمانی نمودار مانند "24h", "7d" و غیره.
     * @param currency واحد پولی که برای نمایش قیمت‌ها استفاده می‌شود.
     * @return Flow<Result<CoinChart>> جریانی که نتیجه دریافت نمودار سکه را ارائه می‌دهد.
     */
    fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Flow<Result<CoinChart>>
}
