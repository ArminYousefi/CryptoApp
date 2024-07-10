package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.remote.model.CoinsApiModel
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.model.Price
import javax.inject.Inject

/**
 * کلاس CoinMapper برای تبدیل مدل API به مدل‌های داخلی سکه استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinMapper @Inject constructor() {

    /**
     * متدی برای تبدیل مدل API به لیستی از مدل‌های داخلی Coin.
     *
     * @param apiModel مدل API که شامل داده‌های سکه‌ها است.
     * @param currency واحد پولی که برای قیمت‌ها استفاده می‌شود.
     * @return List<Coin> لیستی از مدل‌های Coin که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(apiModel: CoinsApiModel, currency: Currency): List<Coin> {
        val validCoins = apiModel.coinsData?.coins
            .orEmpty()
            .filterNotNull()
            .filter { it.id != null }

        return validCoins.map { coinApiModel ->
            Coin(
                id = coinApiModel.id!!, // اینجا اطمینان حاصل می‌شود که id وجود دارد و غیر null است
                name = coinApiModel.name.orEmpty(),
                symbol = coinApiModel.symbol.orEmpty(),
                imageUrl = coinApiModel.imageUrl.orEmpty(),
                currentPrice = Price(coinApiModel.currentPrice, currency = currency),
                priceChangePercentage24h = Percentage(coinApiModel.priceChangePercentage24h),
            )
        }
    }
}

