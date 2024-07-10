package google.yousefi.cryptoapp.data.source.remote

import google.yousefi.cryptoapp.data.source.local.preferences.common.CoinSort
import google.yousefi.cryptoapp.data.source.local.preferences.global.Currency
import google.yousefi.cryptoapp.data.source.remote.model.CoinChartApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinDetailsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinSearchResultsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.CoinsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.FavouriteCoinsApiModel
import google.yousefi.cryptoapp.data.source.remote.model.MarketStatsApiModel
import retrofit2.Response
import javax.inject.Inject

/**
 * پیاده‌سازی منبع داده شبکه‌ای برای دریافت اطلاعات سکه‌ها.
 *
 * @param coinApi رابط API برای ارتباط با سرور.
 */
class CoinNetworkDataSourceImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinNetworkDataSource {

    /**
     * دریافت لیستی از سکه‌ها براساس مرتب‌سازی و ارز مرجع.
     *
     * @param coinSort نوع مرتب‌سازی سکه‌ها.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده سکه‌ها.
     */
    override suspend fun getCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Response<CoinsApiModel> {
        return coinApi.getCoins(
            orderBy = coinSort.getOrderBy(),
            orderDirection = coinSort.getOrderDirection(),
            currencyUUID = currency.toCurrencyUUID()
        )
    }

    /**
     * دریافت لیستی از سکه‌های مورد علاقه براساس شناسه‌ها، نوع مرتب‌سازی و ارز مرجع.
     *
     * @param coinIds لیستی از شناسه‌های یکتای سکه‌های مورد علاقه.
     * @param coinSort نوع مرتب‌سازی سکه‌ها.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده سکه‌های مورد علاقه.
     */
    override suspend fun getFavouriteCoins(
        coinIds: List<String>,
        coinSort: CoinSort,
        currency: Currency
    ): Response<FavouriteCoinsApiModel> {
        return coinApi.getFavouriteCoins(
            coinIds = coinIds,
            orderBy = coinSort.getOrderBy(),
            orderDirection = coinSort.getOrderDirection(),
            currencyUUID = currency.toCurrencyUUID()
        )
    }

    /**
     * دریافت جزئیات یک سکه براساس شناسه و ارز مرجع.
     *
     * @param coinId شناسه یکتای سکه.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده جزئیات سکه.
     */
    override suspend fun getCoinDetails(
        coinId: String,
        currency: Currency
    ): Response<CoinDetailsApiModel> {
        return coinApi.getCoinDetails(
            coinId = coinId,
            currencyUUID = currency.toCurrencyUUID()
        )
    }

    /**
     * دریافت داده‌های نمودار یک سکه براساس شناسه، بازه زمانی و ارز مرجع.
     *
     * @param coinId شناسه یکتای سکه.
     * @param chartPeriod بازه زمانی که نمودار براساس آن محاسبه می‌شود.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده نمودار سکه.
     */
    override suspend fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Response<CoinChartApiModel> {
        return coinApi.getCoinChart(
            coinId = coinId,
            chartPeriod = chartPeriod,
            currencyUUID = currency.toCurrencyUUID()
        )
    }

    /**
     * دریافت نتایج جستجوی سکه‌ها براساس عبارت جستجو.
     *
     * @param searchQuery عبارت جستجو.
     * @return یک پاسخ شامل مدل داده نتایج جستجوی سکه‌ها.
     */
    override suspend fun getCoinSearchResults(
        searchQuery: String
    ): Response<CoinSearchResultsApiModel> {
        return coinApi.getCoinSearchResults(searchQuery = searchQuery)
    }

    /**
     * دریافت آمار بازار.
     *
     * @return یک پاسخ شامل مدل داده آمار بازار.
     */
    override suspend fun getMarketStats(): Response<MarketStatsApiModel> {
        return coinApi.getMarketStats()
    }
}

/**
 * تبدیل ارز به شناسه یکتای ارز مرجع.
 *
 * @return شناسه یکتای ارز مرجع.
 */
private fun Currency.toCurrencyUUID(): String {
    return when (this) {
        Currency.USD -> "yhjMzLPhuIDl"
        Currency.GBP -> "Hokyui45Z38f"
        Currency.EUR -> "5k-_VTxqtCEI"
    }
}

/**
 * دریافت معیار مرتب‌سازی براساس نوع مرتب‌سازی سکه.
 *
 * @return معیار مرتب‌سازی.
 */
private fun CoinSort.getOrderBy(): String {
    return when (this) {
        CoinSort.MarketCap -> "marketCap"
        CoinSort.Popular -> "24hVolume"
        CoinSort.Gainers -> "change"
        CoinSort.Losers -> "change"
        CoinSort.Newest -> "listedAt"
    }
}

/**
 * دریافت جهت مرتب‌سازی براساس نوع مرتب‌سازی سکه.
 *
 * @return جهت مرتب‌سازی.
 */
private fun CoinSort.getOrderDirection(): String {
    return when (this) {
        CoinSort.MarketCap -> "desc"
        CoinSort.Popular -> "desc"
        CoinSort.Gainers -> "desc"
        CoinSort.Losers -> "asc"
        CoinSort.Newest -> "desc"
    }
}
