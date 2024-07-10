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

/**
 * منبع داده شبکه‌ای برای دریافت اطلاعات سکه‌ها.
 */
interface CoinNetworkDataSource {
    /**
     * دریافت لیستی از سکه‌ها براساس مرتب‌سازی و ارز مرجع.
     *
     * @param coinSort نوع مرتب‌سازی سکه‌ها.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده سکه‌ها.
     */
    suspend fun getCoins(
        coinSort: CoinSort,
        currency: Currency
    ): Response<CoinsApiModel>

    /**
     * دریافت لیستی از سکه‌های مورد علاقه براساس شناسه‌ها، نوع مرتب‌سازی و ارز مرجع.
     *
     * @param coinIds لیستی از شناسه‌های یکتای سکه‌های مورد علاقه.
     * @param coinSort نوع مرتب‌سازی سکه‌ها.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده سکه‌های مورد علاقه.
     */
    suspend fun getFavouriteCoins(
        coinIds: List<String>,
        coinSort: CoinSort,
        currency: Currency
    ): Response<FavouriteCoinsApiModel>

    /**
     * دریافت جزئیات یک سکه براساس شناسه و ارز مرجع.
     *
     * @param coinId شناسه یکتای سکه.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده جزئیات سکه.
     */
    suspend fun getCoinDetails(coinId: String, currency: Currency): Response<CoinDetailsApiModel>

    /**
     * دریافت داده‌های نمودار یک سکه براساس شناسه، بازه زمانی و ارز مرجع.
     *
     * @param coinId شناسه یکتای سکه.
     * @param chartPeriod بازه زمانی که نمودار براساس آن محاسبه می‌شود.
     * @param currency ارز مرجع برای تبدیل قیمت‌ها.
     * @return یک پاسخ شامل مدل داده نمودار سکه.
     */
    suspend fun getCoinChart(
        coinId: String,
        chartPeriod: String,
        currency: Currency
    ): Response<CoinChartApiModel>

    /**
     * دریافت نتایج جستجوی سکه‌ها براساس عبارت جستجو.
     *
     * @param searchQuery عبارت جستجو.
     * @return یک پاسخ شامل مدل داده نتایج جستجوی سکه‌ها.
     */
    suspend fun getCoinSearchResults(searchQuery: String): Response<CoinSearchResultsApiModel>

    /**
     * دریافت آمار بازار.
     *
     * @return یک پاسخ شامل مدل داده آمار بازار.
     */
    suspend fun getMarketStats(): Response<MarketStatsApiModel>
}
