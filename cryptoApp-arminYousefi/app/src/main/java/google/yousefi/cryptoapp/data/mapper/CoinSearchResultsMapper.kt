package google.yousefi.cryptoapp.data.mapper

import google.yousefi.cryptoapp.data.source.remote.model.CoinSearchResultsApiModel
import google.yousefi.cryptoapp.model.SearchCoin
import javax.inject.Inject

/**
 * کلاس CoinSearchResultsMapper برای تبدیل مدل API به مدل‌های داخلی نتایج جستجوی سکه استفاده می‌شود.
 *
 * @constructor سازنده کلاس که توسط Dagger برای تزریق وابستگی‌ها استفاده می‌شود.
 */
class CoinSearchResultsMapper @Inject constructor() {

    /**
     * متدی برای تبدیل مدل API به لیستی از مدل‌های داخلی SearchCoin.
     *
     * @param apiModel مدل API که شامل داده‌های نتایج جستجوی سکه‌ها است.
     * @return List<SearchCoin> لیستی از مدل‌های SearchCoin که شامل داده‌های معتبر و تبدیل شده است.
     */
    fun mapApiModelToModel(apiModel: CoinSearchResultsApiModel): List<SearchCoin> {
        val validSearchResultsCoins = apiModel.coinsSearchResultsData?.coinSearchResults
            .orEmpty()
            .filterNotNull()
            .filter { it.id != null }

        return validSearchResultsCoins.map { searchResultsCoin ->
            SearchCoin(
                id = searchResultsCoin.id!!, // اینجا اطمینان حاصل می‌شود که id وجود دارد و غیر null است
                name = searchResultsCoin.name.orEmpty(),
                symbol = searchResultsCoin.symbol.orEmpty(),
                imageUrl = searchResultsCoin.imageUrl.orEmpty()
            )
        }
    }
}
