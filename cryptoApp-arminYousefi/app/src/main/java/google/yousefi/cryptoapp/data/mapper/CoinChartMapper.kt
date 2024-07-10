package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.common.toSanitisedBigDecimalOrNull
import google.yousefi.cryptoapp.data.source.remote.model.CoinChartApiModel
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.model.CoinChart
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.model.Price
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList

/**
 * کلاس CoinChartMapper برای تبدیل مدل API به مدل داخلی برنامه استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinChartMapper @Inject constructor() {

    /**
     * متدی برای تبدیل مدل API به مدل CoinChart.
     *
     * @param apiModel مدل API که شامل داده‌های نمودار سکه است.
     * @param currency واحد پولی که برای قیمت‌ها استفاده می‌شود.
     * @return CoinChart مدل CoinChart که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(apiModel: CoinChartApiModel, currency: Currency): CoinChart {
        val validPrices = apiModel.coinChartData?.pastPrices
            .orEmpty()
            .mapNotNull { pastPrice ->
                pastPrice?.amount.toSanitisedBigDecimalOrNull()
            }
            .filter { price -> price.compareTo(BigDecimal.ZERO) >= 0 }
            .reversed()

        val minPrice = when {
            validPrices.isNotEmpty() -> validPrices.minOrNull().toString()
            else -> null
        }

        val maxPrice = when {
            validPrices.isNotEmpty() -> validPrices.maxOrNull().toString()
            else -> null
        }

        return CoinChart(
            prices = validPrices.toPersistentList(),
            minPrice = Price(minPrice, currency = currency),
            maxPrice = Price(maxPrice, currency = currency),
            periodPriceChangePercentage = Percentage(apiModel.coinChartData?.priceChangePercentage)
        )
    }
}
