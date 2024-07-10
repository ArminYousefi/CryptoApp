package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import google.yousefi.cryptoapp.data.source.remote.model.FavouriteCoinsApiModel
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.model.Percentage
import google.yousefi.cryptoapp.model.Price
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList

/**
 * کلاس FavouriteCoinMapper برای تبدیل مدل API به مدل‌های داخلی سکه‌های مورد علاقه استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class FavouriteCoinMapper @Inject constructor() {

    /**
     * متدی برای تبدیل مدل API به لیستی از مدل‌های داخلی FavouriteCoin.
     *
     * @param apiModel مدل API که شامل داده‌های سکه‌های مورد علاقه است.
     * @param currency واحد پولی که برای قیمت‌ها استفاده می‌شود.
     * @return List<FavouriteCoin> لیستی از مدل‌های FavouriteCoin که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(
        apiModel: FavouriteCoinsApiModel,
        currency: Currency
    ): List<FavouriteCoin> {
        val validFavouriteCoins = apiModel.favouriteCoinsData?.favouriteCoins
            .orEmpty()
            .filterNotNull()
            .filter { it.id != null }

        return validFavouriteCoins.map { favouriteCoinsApiModel ->
            FavouriteCoin(
                id = favouriteCoinsApiModel.id!!, // اینجا اطمینان حاصل می‌شود که id وجود دارد و غیر null است
                name = favouriteCoinsApiModel.name.orEmpty(),
                symbol = favouriteCoinsApiModel.symbol.orEmpty(),
                imageUrl = favouriteCoinsApiModel.imageUrl.orEmpty(),
                currentPrice = Price(favouriteCoinsApiModel.currentPrice, currency = currency),
                priceChangePercentage24h = Percentage(favouriteCoinsApiModel.priceChangePercentage24h),
                prices24h = favouriteCoinsApiModel.prices24h
                    .orEmpty()
                    .filterNotNull()
                    .filter { price -> price.compareTo(BigDecimal.ZERO) >= 0 }
                    .toPersistentList()
            )
        }
    }
}
